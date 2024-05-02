package me.projects.firstandroidapp.adapter;

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.List;

class AutocompleteCitiesAdapter(context: Context, private val suggestions:MutableList<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, suggestions) {

    // Override the getCount() method to specify the number of suggestions
    override fun getCount(): Int {
        return suggestions.size
    }

    // Override the getItem() method to return the suggestion at the specified position
    override fun getItem(position: Int): String? {
        return suggestions[position]
    }

    // Override the getFilter() method to perform filtering logic
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredSuggestions = mutableListOf<String>()

                constraint?.let { query ->
                    // Perform filtering logic here (e.g., search through suggestions)
                    for (suggestion in suggestions) {
                        if (suggestion.contains(query, ignoreCase = true)) {
                            filteredSuggestions.add(suggestion)
                        }
                    }
                }

                filterResults.values = filteredSuggestions
                filterResults.count = filteredSuggestions.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    if (it.count > 0) {
                        // Apply filtered suggestions to the adapter
                        clear()
                        addAll(it.values as List<String>)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
