package com.example.todolist.iu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    companion object {
        const val CREATE_NEW_TASK =1000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvListTask.adapter = adapter
        upDateList()
        insertListiner()
    }

    private fun insertListiner() {
        binding.bAddTask.setOnClickListener{

            startActivityForResult(Intent(this, AddNewTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            intent.putExtra(AddNewTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            upDateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK){
            upDateList()
        }
    }

    private fun upDateList(){
        val list = TaskDataSource.getList()
        binding.includeEmpty.emptyEstate.visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE

        adapter.submitList(list)
    }


}