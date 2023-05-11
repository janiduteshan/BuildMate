import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mad.buildmate.Item
import com.mad.buildmate.R

class ItemsAdapter(
    private val items: List<Item>,
    private val onActionClicked: (Item, ActionType) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    enum class ActionType {
        UPDATE, DELETE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onActionClicked)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.item_title)
        private val description: TextView = view.findViewById(R.id.item_description)
        private val price: TextView = view.findViewById(R.id.item_price)
        private val contactInfo: TextView = view.findViewById(R.id.item_contact_info)

        fun bind(item: Item, onActionClicked: (Item, ActionType) -> Unit) {
            title.text = item.title
            description.text = item.description
            price.text = item.price
            contactInfo.text = item.contactInfo

            // Set OnClickListener for update and delete buttons
            view.findViewById<Button>(R.id.update_button).setOnClickListener { onActionClicked(item, ActionType.UPDATE) }
            view.findViewById<Button>(R.id.delete_button).setOnClickListener { onActionClicked(item, ActionType.DELETE) }
        }
    }
}
