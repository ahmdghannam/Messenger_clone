package fts.ahmed.messenger_clone.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import fts.ahmed.messenger_clone.R
import fts.ahmed.messenger_clone.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val cameraBtnOnClick = MutableStateFlow<()->Unit> {
        startActivity(Intent(this@MainActivity,CameraActivity::class.java))
    }
    private val penBtnOnClick = MutableStateFlow<()->Unit> {
        startActivity(Intent(this@MainActivity,PenNewMessageActivity::class.java))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initNavController()
        navigationChanges()
        lifecycleScope.launchWhenStarted {
            cameraBtnOnClick.collect(){
                binding.appBarBtnCamera.setOnClickListener {
                    it()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            penBtnOnClick.collect(){
                binding.appBarBtnPen.setOnClickListener {
                    it()
                }
            }
        }
//        binding.profilePicture?.setOnClickListener {
//            startActivity(Intent(this@MainActivity,ProfileActivity::class.java))
//        }
    }
    private fun initNavController() {
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigation.setupWithNavController(navController)
    }
    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun navigationChanges() {

        fun showAppBarButtons() {
            binding.appBarBtnCamera.visibility = View.VISIBLE
            binding.appBarBtnPen.visibility = View.VISIBLE
        }
        fun hideAppBarButtons() {
            binding.appBarBtnCamera.visibility = View.INVISIBLE
            binding.appBarBtnPen.visibility = View.INVISIBLE
        }

        //fragments
        fun setUpStoriesFragment() {
            findNavController(R.id.fragmentContainerView).navigate(R.id.storiesFragment)
            binding.appBarTitle.text = getString(R.string.stories)
            hideAppBarButtons()
        }
        fun setUpPeopleFragment() {
            findNavController(R.id.fragmentContainerView).navigate(R.id.peopleFragment)
            showAppBarButtons()
            binding.appBarTitle.text = getString(R.string.people)
            binding.appBarBtnPen.setImageResource(R.drawable.ic_contacts)
            binding.appBarBtnCamera.visibility = View.INVISIBLE
            lifecycleScope.launch {
                penBtnOnClick.emit {
                    startActivity(Intent(this@MainActivity,ContactsActivity::class.java))
                }
            }
        }
        fun setUpCallsFragment() {
            showAppBarButtons()
            findNavController(R.id.fragmentContainerView).navigate(R.id.callsFragment)
            binding.appBarTitle.text = getString(R.string.calls)
            binding.appBarBtnCamera.setImageResource(R.drawable.ic_call)
            binding.appBarBtnPen.setImageResource(R.drawable.ic_videocam)
            lifecycleScope.launch {
                cameraBtnOnClick.emit {
                    startActivity(Intent(this@MainActivity,AudioCallActivity::class.java))
                }
            }
            lifecycleScope.launch {
                penBtnOnClick.emit {
                    startActivity(Intent(this@MainActivity,VideoCallActivity::class.java))
                }
            }
        }
        fun setUpChatsFragment() {
            showAppBarButtons()
            findNavController(R.id.fragmentContainerView).navigate(R.id.chatsFragment)
            binding.appBarTitle.text = getString(R.string.chats)
            binding.appBarBtnCamera.setImageResource(R.drawable.ic_camera)
            binding.appBarBtnPen.setImageResource(R.drawable.ic_pen)
            lifecycleScope.launch {
                cameraBtnOnClick.emit {
                    startActivity(Intent(this@MainActivity,CameraActivity::class.java))
                }

            }
            lifecycleScope.launch {
                penBtnOnClick.emit {
                    startActivity(Intent(this@MainActivity,PenNewMessageActivity::class.java))
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chatsFragment -> setUpChatsFragment()
                R.id.callsFragment -> setUpCallsFragment()
                R.id.peopleFragment -> setUpPeopleFragment()
                R.id.storiesFragment -> setUpStoriesFragment()
            }
            true
        }
    }
}