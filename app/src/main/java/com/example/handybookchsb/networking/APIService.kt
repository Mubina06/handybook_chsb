package com.example.handybookchsb.networking


import com.example.handybookchsb.model.AddComment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.handybookchsb.model.Book
import com.example.handybookchsb.model.Category
import com.example.handybookchsb.model.Comment
import com.example.handybookchsb.model.Login
import com.example.handybookchsb.model.UserReg
import com.example.handybookchsb.model.f
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("/book-api/login")
    fun login(@Body loginRequest: Login): Call<f>
    @POST("/book-api/register")
    fun register(@Body regRequest: UserReg):Call<UserReg>

    @GET("/book-api")
    fun getAllBooks(): Call<List<Book>>

    @GET("/book-api/all-category")
    fun getCategories():Call<List<Category>>

    @GET("/book-api/category")
    fun getBookByCategory(@Query("name") name:String):Call<List<Book>>

    @GET("/book-api/category")
    fun getBooksByCategory(@Query("name") categoryName: String):Call<List<Book>>

    @GET("/book-api/search-name")
    fun search(@Query("name") name: String):Call<List<Book>>

    @GET("/book-api/main-book")
    fun getMainBook():Call<Book>

    @GET("/book-api/view")
    fun getBookById(@Query("id") id: Int): Call<Book>

    @GET("/book-api/comment")
    fun getBookComment(@Query("id") id :Int): Call<List<Comment>>

    @POST("/comment-api/create")
    fun addComment(@Body comment: AddComment) : Call<AddComment>
}