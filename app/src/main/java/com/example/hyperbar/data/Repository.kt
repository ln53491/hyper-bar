import android.content.ContentValues
import android.util.Log
import com.example.hyperbar.data.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class Repository{

    val database = Firebase.database("https://hyper-bar-default-rtdb.europe-west1.firebasedatabase.app/")

    fun addOrder(order: Order){
        if(bool){
            database.setPersistenceEnabled(true)
            bool = false
        }
        var orders = database.getReference("order")
        orders.push().setValue(order)
    }

    fun getCategories(): Flow<List<CategoryNew>> = callbackFlow  {
        if(bool){
            database.setPersistenceEnabled(true)
            bool = false
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items: List<CategoryNew> = dataSnapshot.children.map { snapshot ->
                    snapshot.getValue(CategoryNew::class.java)!!
                }
                this@callbackFlow.trySend(items).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }

        var categories = database.getReference("categories")
        categories.addValueEventListener(listener)
        awaitClose{ categories.removeEventListener(listener) }
    }

    fun getTodaysSelection(): Flow<List<TodaysSelection>> = callbackFlow  {
        if(bool){
            database.setPersistenceEnabled(true)
            bool = false
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items: List<TodaysSelection> = dataSnapshot.children.map { snapshot ->
                    snapshot.getValue(TodaysSelection::class.java)!!
                }
                this@callbackFlow.trySend(items).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }

        var todaysSelection = database.getReference("todaysSelection")
        todaysSelection.addValueEventListener(listener)
        awaitClose{ todaysSelection.removeEventListener(listener) }
    }

    fun getProducts(): Flow<List<Product>> = callbackFlow  {
        if(bool){
            database.setPersistenceEnabled(true)
            bool = false
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items: List<Product> = dataSnapshot.children.map { snapshot ->
                    snapshot.getValue(Product::class.java)!!
                }
                this@callbackFlow.trySend(items).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }

        var products = database.getReference("products")
        products.addValueEventListener(listener)
        awaitClose{ products.removeEventListener(listener) }
    }

    fun getOrders(): Flow<List<Order>> = callbackFlow  {
        if(bool){
            database.setPersistenceEnabled(true)
            bool = false
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items: List<Order> = dataSnapshot.children.map { snapshot ->
                    snapshot.getValue(Order::class.java)!!
                }
                this@callbackFlow.trySend(items).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        }

        var orders = database.getReference("order")
        orders.addValueEventListener(listener)
        awaitClose{ orders.removeEventListener(listener) }
    }
}
