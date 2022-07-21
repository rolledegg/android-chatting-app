package smu.app.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import smu.app.chattingapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var msgAdapter:MessageAdapter
    private lateinit var msgList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom:String? = null
    var senderRoom:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef= FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom= senderUid + receiverUid

        supportActionBar?.title = name

        msgList = ArrayList()
        msgAdapter = MessageAdapter(this,msgList)

        binding.chatRcv.layoutManager = LinearLayoutManager(this)
        binding.chatRcv.adapter = msgAdapter

        //logic for adding data to recyclerView
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    msgList.clear()

                    for(postSnapShot in snapshot.children){

                        val msg = postSnapShot.getValue(Message::class.java)
                        msgList.add(msg!!)
                    }
                    msgAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        //adding the msg to database
        binding.sendBtn.setOnClickListener{
            val msg = binding.msgEdt.text.toString()
            val msgObject = Message(msg, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(msgObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(msgObject)
                }

            binding.msgEdt.setText("")
        }
    }
}