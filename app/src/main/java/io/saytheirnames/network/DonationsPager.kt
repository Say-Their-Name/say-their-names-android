package io.saytheirnames.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.DiffUtil
import io.saytheirnames.adapters.DonationAdapter
import io.saytheirnames.adapters.PeopleAdapter
import io.saytheirnames.adapters.PetitionsAdapter
import io.saytheirnames.models.Donation
import io.saytheirnames.models.People
import io.saytheirnames.models.Petition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

class DonationsPager(val adapter: DonationAdapter): Any() {

    fun loadDonationsFromPagination() {
        GlobalScope.launch {
            getDonationsPager().collectLatest {
                try {
                    adapter.submitData(it)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getDonationsPager() = Pager<Int, Donation>(
            config = PagingConfig(
                    pageSize = 12,
                    prefetchDistance = 12
            ),
            initialKey = 1
    ) {
        DonationsDataSource()
    }.flow

    companion object {
        fun getDiffItemCallback() = object: DiffUtil.ItemCallback<Donation>() {
            override fun areItemsTheSame(oldItem: Donation, newItem: Donation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Donation, newItem: Donation): Boolean {
               return oldItem.id == newItem.id
                       && oldItem.identifier == newItem.identifier
                       && oldItem.title == newItem.title
            }
        }
    }

}