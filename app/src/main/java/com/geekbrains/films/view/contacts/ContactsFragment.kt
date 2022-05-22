package com.geekbrains.films.view.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.geekbrains.films.R
import com.geekbrains.films.databinding.ContactsFragmentBinding

class ContactsFragment : Fragment() {
    private var _binding: ContactsFragmentBinding? = null
    private val binding get() = _binding!!

    private val permissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getContacts()
        } else {
            Toast.makeText(
                context,
                getString(R.string.need_permissions_to_read_contacts),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermissions() {
        context?.let { context_ ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(context_, Manifest.permission.READ_CONTACTS) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun getContacts() {
        context?.let { context_ ->
            val cursor: Cursor? = context_.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let { cursor_ ->
                for (i in 0..cursor_.count) {
                    if (cursor_.moveToPosition(i)) {
                        val col = cursor_.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        assert(col >= 0)
                        val name = cursor_.getString(col)
                        addView(name)
                    }
                }
                cursor_.close()
            }
        }
    }

    private fun addView(name: String) = with(binding) {
        contactsContainer.addView(TextView(requireContext()).apply {
            text = name
            textSize = resources.getDimension(R.dimen.main_container_text_size)
        })
    }
}