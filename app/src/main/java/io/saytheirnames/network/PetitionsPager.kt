package io.saytheirnames.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.DiffUtil
import io.saytheirnames.adapters.PetitionsAdapter
import io.saytheirnames.models.Petition
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PetitionsPager(val adapter: PetitionsAdapter): Any() {

    fun loadPetitionsFromPagination() {
        GlobalScope.launch {
            getPetitionsPager().collectLatest {
                try {
                    adapter.submitData(it)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getPetitionsPager() = Pager<Int, Petition>(
            config = PagingConfig(
                    pageSize = 12,
                    prefetchDistance = 12
            ),
            initialKey = 1
    ) {
        PetitionsDataSource()
    }.flow

    companion object {
        fun getDiffItemCallback() = object: DiffUtil.ItemCallback<Petition>() {
            override fun areItemsTheSame(oldItem: Petition, newItem: Petition): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Petition, newItem: Petition): Boolean {
               return oldItem.id == newItem.id
                       && oldItem.identifier == newItem.identifier
                       && oldItem.title == newItem.title
            }
        }
    }

}