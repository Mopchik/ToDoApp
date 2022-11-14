package com.mopchik.planner

import android.util.Log
import androidx.annotation.MainThread
import com.google.gson.Gson
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.*
import kotlin.concurrent.thread


interface Api {
    @Headers("Accept: application/json")
    @GET("/list")
    suspend fun getToDoItemList(): List<ToDoItem>

    @Headers("Accept: application/json")
    @GET("/list/{id}")
    suspend fun getToDoItem(@Path("id")id: Int): ToDoItem

    @POST("/post")
    suspend fun postToDoItem()
}
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val server = MockWebServer()

    private val answerList = "[{\"id\":0," +
            "\"text\":\"\"," +
            "\"importance\":\"NO\"," +
            "\"isDone\":false," +
            "\"creationDate\":{\"year\":2022," +
            "\"month\":10,\"dayOfMonth\":6," +
            "\"hourOfDay\":11,\"minute\":48,\"second\":10}}]"
    @Before
    fun startServer(){
        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if(request.path == "/list") {
                    println("list command")
                    return MockResponse().setResponseCode(200)
                        .setBody(answerList)
                }
                else if(request.path?.startsWith("/list/") == true) {
                    println("id command")
                    val uuid = request.path!!.subSequence(
                        6, request.path!!.length).toString().toInt()
                    if(uuid < 0)
                        return MockResponse().setResponseCode(400)
                    return MockResponse().setResponseCode(200)
                        .setBody(
                            "{\"id\":$uuid," +
                                    "\"text\":\"\"," +
                                    "\"importance\":\"NO\"," +
                                    "\"isDone\":false," +
                                    "\"creationDate\":{\"year\":2022," +
                                    "\"month\":10,\"dayOfMonth\":6," +
                                    "\"hourOfDay\":11,\"minute\":48,\"second\":10}}"
                        )
                }
                println("Non of them")
                return MockResponse().setResponseCode(404).setBody("Haha loh")
            }
        }
        server.dispatcher = dispatcher

        // Schedule some responses.
        // server.enqueue(MockResponse().setBody("hello, world!"))
        // server.enqueue(MockResponse().setBody("sup, bra?"))
        // server.enqueue(MockResponse().setBody("yo dog"))

        // Start the server.
        server.start()
        println("server started")
    }

    @Test
    fun addition_isCorrect() {
        //val baseUrl = server.url("")
            //
            //val clientSocket = Socket(baseUrl.host, baseUrl.port)
                //val req = "GET /list HTTP/1.1\n\n"
                    //val input = BufferedReader(InputStreamReader(
        //    clientSocket.getInputStream()
                        //))
        //val output = PrintWriter(
        //    OutputStreamWriter(
            //        clientSocket.getOutputStream()
            //    )
        //)
        //output.println(req)
            //output.flush()
            //val s = input.readLines()
                //input.close()
                //output.close()
                //clientSocket.close()
                //assert(s.subList(3, s.size).joinToString("\n") == answerList)
                //
                //val urlConnection = server.url("/list/2").toUrl().openConnection()
                    //    as HttpURLConnection
        //urlConnection.requestMethod = "GET"
                    //urlConnection.doInput = true
                    //val reader = InputStreamReader(urlConnection.inputStream)
                        //val k = reader.readText()
                            //println(k)
                            //
                            //println(Gson().toJson(ToDoItem()))
                            //println(Gson().toJson(listOf(ToDoItem())))
                            //
                            //val client = OkHttpClient()
                                //val url = server.url("/list/8")
                                    //    .newBuilder()
                                    //    .build()
                                    //    .toString()
                                    //val request = Request.Builder()
                                        //    .url(url)
                                        //    .method("GET", null)
        //    .build()
        //client.newCall(request).enqueue(responseCallback = object : Callback {
        //    override fun onFailure(call: Call, e: IOException) {
        //        Log.e("KOSTIK", "Bad result from the network.")
                    //    }
        //    override fun onResponse(call: Call, response: Response) {
        //        println(response.body?.string())
                    //    }
        //})
            //Thread.sleep(200)

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(Api::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val list = api.getToDoItemList()
            println(list[0])
            val item = api.getToDoItem(421)
            println(item)
            try {
                api.getToDoItem(-2)
            } catch (e: HttpException){
                when(e.code()){
                    400 -> println("You can't ask for non positive id")
                }
            }
        }
        Thread.sleep(1000)
    }
    @MainThread
    fun eee(){}
    @After
    fun stopServer(){
        // Shut down the server. Instances cannot be reused.
        server.shutdown()
    }
}


class ResultAdapterFactory: CallAdapter.Factory(){
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<Type, Call<Type>>? =
        when(getRawType(returnType)){
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            ErrorConverterAdapter(callType)
        }
        else -> null
    }
}

class ErrorConverterAdapter(private val type: Type): CallAdapter<Type, Call<Type>>{
    override fun responseType(): Type = type
    override fun adapt(call: Call<Type>): Call<Type> = ErrorConverterCall(call, type)
}

class ErrorConverterCall<T>(val delegate: Call<T>, val type: Type): Call<T> by delegate{
    override fun clone(): ErrorConverterCall<T> = ErrorConverterCall(delegate.clone(), type)

    override fun enqueue(callback: Callback<T>) {
        delegate.enqueue(object : Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful) callback.onResponse(call, response)
                else callback.onFailure(call, safeConvert(HttpException(response)))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFailure(call, safeConvert(t))
            }

        })
    }

    private fun safeConvert(throwable: Throwable): Throwable{
        return kotlin.runCatching {
            when(throwable){
                is HttpException -> IllegalArgumentException(throwable.message)
                else -> throwable
            }
        }.getOrElse{throwable}
    }
}