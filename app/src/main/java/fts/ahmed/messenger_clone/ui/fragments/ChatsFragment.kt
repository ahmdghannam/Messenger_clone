package fts.ahmed.messenger_clone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fts.ahmed.messenger_clone.R

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_chats, container, false)
    }


}