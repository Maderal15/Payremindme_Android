package br.com.maderal.payremind_me.person

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.maderal.payremind_me.R
import br.com.maderal.payremind_me.api.PayRemindMeAPIService
import br.com.maderal.payremind_me.model.*
import kotlinx.android.synthetic.main.activity_lista.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaPessoasActivity : AppCompatActivity(), OnDeleteListener, OnEditListener {


    private val REQUEST_CODE = 101

    private lateinit var personAdapter: PersonAdapter

    private var payRemindMeAPIService : PayRemindMeAPIService? = null
    private var PREFS_FILENAME = "payremind.prefs"
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        this.payRemindMeAPIService = PayRemindMeAPIService(prefs)


        this.payRemindMeAPIService?.getPessoas()?.enqueue(object :
            Callback<PersonList> {
            override fun onFailure(call: Call<PersonList>?, t: Throwable?) {

                t?.printStackTrace()
                Toast.makeText(
                    this@ListaPessoasActivity,
                    t?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<PersonList>?, response: Response<PersonList>?) =
                if (response?.isSuccessful == true) {
                    val personList = response.body()?.content

                    if (personList != null) {
                        personAdapter = PersonAdapter(personList.toMutableList(), this@ListaPessoasActivity, this@ListaPessoasActivity)
                    }
                    recyclerView.adapter = personAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@ListaPessoasActivity, LinearLayoutManager.VERTICAL, false)

                } else {

                    Toast.makeText(
                        this@ListaPessoasActivity,
                        "Erro ao buscar pessoas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        })
    }


    override fun deleteItem(person: Person) {
        personAdapter.listPerson.remove(person)
        personAdapter.notifyDataSetChanged()
    }

    override fun editItem(person: Person, index: Int) {
        val intent = Intent(this, CadastroActivity::class.java)
        intent.putExtra(PERSON_INTENT_EDIT, true)
        intent.putExtra(PERSON_INTENT_OBJECT, person)
        intent.putExtra(PERSON_INTENT_INDEX, index)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun openCadastro(view: View) {
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val bundle = data.extras
                val isEdit = bundle.getBoolean(PERSON_INTENT_EDIT, false)
                val person = bundle.getParcelable<Person>(PERSON_INTENT_OBJECT)
                if (isEdit) {
                    val index = bundle.getInt(PERSON_INTENT_INDEX, -1)
                    // something very wrong occurred, do nothing
                    if (index == -1)
                        return
                    personAdapter.listPerson.set(index, person)
                    personAdapter.notifyItemChanged(index)
                } else {
                    personAdapter.listPerson.add(person)
                    personAdapter.notifyItemInserted(personAdapter.listPerson.size - 1)
                }
            }
        }
    }
}
