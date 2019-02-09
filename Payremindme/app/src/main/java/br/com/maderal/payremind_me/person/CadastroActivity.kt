package br.com.maderal.payremind_me.person

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import br.com.maderal.payremind_me.R
import br.com.maderal.payremind_me.api.PayRemindMeAPIService
import br.com.maderal.payremind_me.model.PERSON_INTENT_EDIT
import br.com.maderal.payremind_me.model.PERSON_INTENT_INDEX
import br.com.maderal.payremind_me.model.PERSON_INTENT_OBJECT
import br.com.maderal.payremind_me.model.Person
import kotlinx.android.synthetic.main.activity_cadastro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    var isEdit = false
    var index = -1
    var person = Person()

    private var payRemindMeAPIService : PayRemindMeAPIService? = null
    private var PREFS_FILENAME = "payremind.prefs"
    private var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        this.payRemindMeAPIService = PayRemindMeAPIService(prefs)

        val estados = resources.getStringArray(R.array.arry_estados)

        //Adapter for spinner
        state.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)

        //item selected listener for spinner
        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               // Toast.makeText(this@CadastroActivity, estados[p2], LENGTH_LONG).show()
            }

        }

        setupActivity()
    }


    private fun setButtonEditListener(){
        //Essaa parte vc set as variaesi quando precisado no butao
        btbm_cadastro.setOnClickListener {
                person.nome = name.text.toString()
                person.endereco.logradouro = address.text.toString()
                person.endereco.numero = number.text.toString().toLong()
                person.endereco.complemento = complement.text.toString()
                person.endereco.bairro = neighborhood.text.toString()
                person.endereco.cep = zip_code.text.toString()
                savePerson()
        }
    }

    private fun savePerson(){
        if(isEdit){
            this.payRemindMeAPIService?.updatePerson(person)?.enqueue(object :
                Callback<Person> {
                override fun onFailure(call: Call<Person>?, t: Throwable?) {

                    t?.printStackTrace()
                    Toast.makeText(
                        this@CadastroActivity,
                        t?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Person>?, response: Response<Person>?) {
                    if (response?.isSuccessful == true) {
                        Toast.makeText(
                            this@CadastroActivity,
                            "Pessoa atualizada.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent()
                        val bundle = Bundle()
                        bundle.putBoolean(PERSON_INTENT_EDIT, isEdit)
                        bundle.putInt(PERSON_INTENT_INDEX, index)
                        bundle.putParcelable(PERSON_INTENT_OBJECT, person)
                        intent.putExtras(bundle)

                        setResult(Activity.RESULT_OK, intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@CadastroActivity,
                            "Erro ao atualizar pessoa",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }else{

            person.ativo = true
            this.payRemindMeAPIService?.addPerson(person)?.enqueue(object :
                Callback<Person> {
                override fun onFailure(call: Call<Person>?, t: Throwable?) {

                    t?.printStackTrace()
                    Toast.makeText(
                        this@CadastroActivity,
                        t?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Person>?, response: Response<Person>?) {
                    if (response?.isSuccessful == true) {
                        person.ativo = !person.ativo
                        Toast.makeText(
                            this@CadastroActivity,
                            "Pessoa adicionada",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent()
                        val bundle = Bundle()
                        bundle.putBoolean(PERSON_INTENT_EDIT, isEdit)
                        bundle.putInt(PERSON_INTENT_INDEX, index)
                        bundle.putParcelable(PERSON_INTENT_OBJECT, person)
                        intent.putExtras(bundle)

                        setResult(Activity.RESULT_OK, intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@CadastroActivity,
                            "Erro ao adicionar pessoa",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }

    }

    private fun setupActivity(){
        val intent = intent
        isEdit = intent.getBooleanExtra(PERSON_INTENT_EDIT, false)
        if(isEdit){
            index = intent.getIntExtra(PERSON_INTENT_INDEX, -1)
            person = intent.getParcelableExtra(PERSON_INTENT_OBJECT)

            name.setText(person.nome)
            address.setText(person.endereco.logradouro)
            number.setText(person.endereco.numero.toString())
            complement.setText(person.endereco.complemento)
            neighborhood.setText(person.endereco.bairro)
            zip_code.setText(person.endereco.cep)
            city.setText("São Paulo")
            state.setSelection(0)

            btbm_cadastro.text = getString(R.string.txt_editt)
            textTitulo.text =  getString(R.string.txt_editt)
        }else{

            city.setText("São Paulo")
            state.setSelection(0)

            btbm_cadastro.text = getString(R.string.txt_cadastro)
            textTitulo.text = getString(R.string.txt_cadastro)

        }

        setButtonEditListener()
    }
}
