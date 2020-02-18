package com.baehoons.aws_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    var ddd :String = editText.text.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        button.setOnClickListener {

            if(ddd.isEmpty() || ddd.equals(" ")){
                Toast.makeText(this,"Enter",Toast.LENGTH_SHORT).show()
            }
            else{
                editText.setText("")

                val response = getApiResponse(ddd)

                val apiResponse = Gson().fromJson(response,ApiResponse::class.java)

                if(apiResponse != null){
                    if(apiResponse.text != null){
                        textView.setText(apiResponse.text)
                    }
                    else{
                        textView.setText("Any results not found")
                    }
                }
            }
        }
    }

    private fun getApiResponse(text:String): String {
        try {
            val apiurl:URL = URL("https://qtz8h3u1b0.execute-api.ap-northeast-2.amazonaws.com/alpha/testing_dynamo"+ddd)
            //val connection:HttpsURLConnection = (HttpsURLConnection) apiurl.openConnection()
            with(apiurl.openConnection() as HttpsURLConnection){
                doOutput=true
                requestMethod="POST"

                val bufferedReader = BufferedReader(InputStreamReader(inputStream))

                val stringBuilder = StringBuilder()

                var jsonOutput:String


                do{
                    jsonOutput = bufferedReader.readLine()
                    if(jsonOutput == null)
                        break
                    stringBuilder.append(jsonOutput)
                } while (true)
                return stringBuilder.toString()
            }
        }
        catch (e:IOException){
            return "Any results not found"
        }

    }

}
