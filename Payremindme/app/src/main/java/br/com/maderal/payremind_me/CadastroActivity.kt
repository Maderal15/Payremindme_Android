package br.com.maderal.payremind_me

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import br.com.maderal.payremind_me.edit.PERSON_INTENT_EDIT
import br.com.maderal.payremind_me.edit.PERSON_INTENT_INDEX
import br.com.maderal.payremind_me.edit.PERSON_INTENT_OBJECT
import br.com.maderal.payremind_me.model.Person
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    var isEdit = false
    var index = -1
    var person = Person()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        //String array.
        val estados =  arrayOf("Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará",
            "Distrito Federal", "Espírito Santo", "Goiás", "Maranhão",
            "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Maranhão",
          "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte",
            "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins")
        //val estados = arrayOf(R.array.arry_estados)

        //Adapter for spinner
        mySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estados)

        //item selected listener for spinner
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@CadastroActivity, estados[p2], LENGTH_LONG).show()
            }

        }

        setupActivity()


    }


    private fun setButtonEditListener(){
        //Essaa parte vc set as variaesi quando precisado no butao
        btbm_cadastro.setOnClickListener {
            /*    person.codigo = editTextFirstName.text.toString()
                person.nome = editTextLastName.text.toString()

                val intent = Intent()
                val bundle = Bundle()
                bundle.putBoolean(PERSON_INTENT_EDIT, isEdit)
                bundle.putInt(PERSON_INTENT_INDEX, index)
                bundle.putParcelable(PERSON_INTENT_OBJECT, person)
                intent.putExtras(bundle)

                setResult(Activity.RESULT_OK, intent)
                finish()*/
            finish()
        }
    }

    private fun setupActivity(){
        val intent = intent
        isEdit = intent.getBooleanExtra(PERSON_INTENT_EDIT, false)
        if(isEdit){
            index = intent.getIntExtra(PERSON_INTENT_INDEX, -1)
            person = intent.getParcelableExtra(PERSON_INTENT_OBJECT)
            name.setText(person.nome)
            btbm_cadastro.text = getString(R.string.txt_editt)
            textTitulo.text =  getString(R.string.txt_editt)
        }else{
            btbm_cadastro.text = getString(R.string.txt_cadastro)
            textTitulo.text = getString(R.string.txt_cadastro)

        }

        setButtonEditListener()

    }
}
