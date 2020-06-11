package io.saytheirnames.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.DiffUtil
import io.saytheirnames.adapters.PeopleAdapter
import io.saytheirnames.models.People
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

class PeoplePager(val adapter: PeopleAdapter): Any() {

    fun loadPeopleFromPagination() {
        GlobalScope.launch {
            getPeoplePager().collectLatest {
                try {
                    adapter.submitData(it)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getPeoplePager() = Pager<Int, People>(
            config = PagingConfig(
                    pageSize = 12,
                    prefetchDistance = 12
            ),
            initialKey = 1
    ) {
        PeopleDataSource()
    }.flow

    companion object {
        fun getDiffItemCallback() = object: DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
               return oldItem.id == newItem.id
                       && oldItem.dateOfIncident == newItem.dateOfIncident
                       && oldItem.fullName == newItem.fullName
            }
        }
    }

}