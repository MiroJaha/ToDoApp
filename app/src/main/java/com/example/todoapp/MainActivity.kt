package com.example.todoapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var outMainL : ConstraintLayout
    private lateinit var RecyclerView : RecyclerView
    private lateinit var addButton : FloatingActionButton
    private lateinit var adapter : RecyclerViewAdapter
    private lateinit var dialogBuilder : AlertDialog.Builder

    private lateinit var toDoList : ArrayList<Group>

    private fun enterData(){

        val input = EditText(this)
        input.hint = "Enter Task Here"
        dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Please Enter Task to Do:")
            .setCancelable(false)
            .setPositiveButton("ADD") {
                    dialog,id -> val str =input.text.toString()
                if(str.isNotBlank()) {
                toDoList.add(Group(str))
                Toast.makeText(
                    this,
                    "New Task Added Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
                else
                    Snackbar.make(
                        outMainL,
                        "Please Enter Task",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            .setNegativeButton("Cancel") {dialog,id -> dialog.cancel()}

        val alert = dialogBuilder.create()
        alert.setTitle("Add New Task")
        alert.setView(input)
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> {
                if (toDoList.isEmpty())
                    Snackbar.make(outMainL,"There is no Task to Delete",Snackbar.LENGTH_LONG).show()
                else {
                    var countDeletedItem = 0
                    for(i in toDoList)
                        if (i.checkBox)
                            countDeletedItem++
                    if (countDeletedItem>0)
                    {
                        Toast.makeText(this,"$countDeletedItem Tasks Deleted",Toast.LENGTH_LONG).show()
                        adapter.DeleteChecked()
                    }
                    else
                        Snackbar.make(outMainL,"Nothing Selected to Delete",Snackbar.LENGTH_LONG).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toDoList= arrayListOf()

        outMainL = findViewById(R.id.mainL)
        RecyclerView = findViewById(R.id.mainRV)
        addButton = findViewById(R.id.addButton)
        adapter = RecyclerViewAdapter(toDoList)

        RecyclerView.adapter = adapter
        RecyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener{
            enterData()
            RecyclerView.adapter?.notifyDataSetChanged()
        }

        adapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })

    }
}