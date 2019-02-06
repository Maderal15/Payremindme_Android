package br.com.maderal.payremind_me.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import java.io.IOException


class LoginInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val t1 = System.nanoTime()
        request = processRequest(request)


        var response = chain.proceed(request)
        val t2 = System.nanoTime()


        response = processResponse(response)
        return response
    }

    //Add cookies for all subsequent requests
    private fun processRequest(request: Request): Request {
        var request = request
        if (!request.url().toString().contains("/v1/authorize")) {
            //  final EmployeeSession session = getSession();
            val etokenAndUId = StringBuilder("etoken=")
            etokenAndUId.append("token")
            request = request.newBuilder().addHeader("Cookie", etokenAndUId.toString()).build()
        }
        return request
    }

    //Extract cookies from login url
    private fun processResponse(response: Response): Response {
        if (response.request().url().toString().contains("/v1/authorize")) {
            val cookieHeaders = response.headers("Set-Cookie")
            var etoken = ""
            var uId = ""
            for (cookieHeader in cookieHeaders) {
                if (cookieHeader.contains("etoken=")) {
                    etoken = cookieHeader.substring(7, cookieHeader.indexOf(';'))
                } else if (cookieHeader.contains("uId=")) {
                    uId = cookieHeader.substring(4, cookieHeader.indexOf(';'))
                }
            }
            //   setSession(etoken, uId, "c1");
            //  logger.info("current employee session:{}", getSession());
        }
        return response
    }
}