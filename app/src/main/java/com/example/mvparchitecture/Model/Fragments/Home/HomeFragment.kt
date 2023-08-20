package com.example.mvparchitecture.Model.Fragments.Home


import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvparchitecture.Model.Fragments.PopUpFragment
import com.example.mvparchitecture.Model.Task
import com.example.mvparchitecture.Presenter.Presenter
import com.example.mvparchitecture.R
import com.example.mvparchitecture.RecycleView.RecycleAdapter
import com.example.mvparchitecture.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class HomeFragment : Fragment(), Presenter.HomeAction , PopUpFragment.PopUpInterface{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private var listtask = ArrayList<Task>()
    private lateinit var usertask: DatabaseReference
    private lateinit var recycleAdapter: RecycleAdapter
    private lateinit var popUpFragment: PopUpFragment
    private lateinit var presenter: Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setevents()
        setswipe()


    }

    private fun setswipe() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = listtask[viewHolder.absoluteAdapterPosition]
                if (direction == ItemTouchHelper.RIGHT) {
                    showdialog()
                    popUpFragment.initprevious(id)
                } else {
                    presenter.deleteswipe(id)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.blue
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.edit)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    .addCornerRadius(0, 20)
                    .create().decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )


            }

        }).attachToRecyclerView(binding.recycleview)
    }


    private fun setevents() {
        binding.btnadd.setOnClickListener {
            showdialog()
        }
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        usertask = FirebaseDatabase.getInstance().reference.child("Note")
            .child(auth.currentUser!!.uid)

        getlisk()
        recycleAdapter = RecycleAdapter()
        recycleAdapter.initlist(listtask!!)
        binding.recycleview.layoutManager = LinearLayoutManager(context)
        binding.recycleview.adapter = recycleAdapter
        popUpFragment = PopUpFragment()
        presenter = Presenter()
        presenter.inithomeaction(this)
//        customDialog = CustomDialog(requireActivity())

    }

    private fun getlisk() {

        usertask.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listtask.clear()
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        i.key?.let {
                            val newtask = i.getValue(Task::class.java)
                            val task = Task(newtask?.note!!, newtask?.des!!)
                            task.id = it
                            listtask.add(task)
                        }
                    }
                }
                recycleAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

// show dialog


    fun showdialog() {
        if (popUpFragment != null) {
            childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()

        }
        popUpFragment = PopUpFragment()
        popUpFragment.initlistener(this)
        popUpFragment.show(
            childFragmentManager, "ABc"
        )
    }


    override fun deleteswipe(id: Task) {
        usertask.child(id.id!!).removeValue().addOnCompleteListener {
            if (it.isSuccessful) recycleAdapter.notifyDataSetChanged()
            Snackbar.make(binding.recycleview, "", Snackbar.LENGTH_SHORT).setAction(
                "Undo Delete", object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        addnewnotee(id, "")
                    }

                }

            ).show()
        }
    }

    override fun updatenote(note: Task) {
        usertask.child(note.id.toString()).setValue(note).addOnCompleteListener {
            if(it.isSuccessful) Toast.makeText(requireActivity(), "Update", Toast.LENGTH_SHORT).show()
        }
        recycleAdapter.notifyDataSetChanged()
    }

    override fun addnewnotee(note: Task, mess : String) {
        usertask.push().setValue(note).addOnCompleteListener {
            if (it.isSuccessful&&mess!="") Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()

        }
    }
}