    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.google.firebase.firestore.FirebaseFirestore
    import android.widget.EditText
    import com.example.lookin.R


    class SearchActivity : AppCompatActivity() {

        private lateinit var searchEditText: EditText
        private lateinit var storeRecyclerView: RecyclerView
        private lateinit var storeAdapter: StoreAdapter
        private lateinit var firestore: FirebaseFirestore

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search)

            // View 초기화
            searchEditText = findViewById(R.id.searchEditText)
            storeRecyclerView = findViewById(R.id.storeRecyclerView)

            // Firestore 초기화
            firestore = FirebaseFirestore.getInstance()

            // RecyclerView 설정
            storeRecyclerView.layoutManager = LinearLayoutManager(this)
            storeAdapter = StoreAdapter()
            storeRecyclerView.adapter = storeAdapter

            // 검색 기능 구현 예시: EditText의 텍스트 변경 감지
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val searchText = s.toString().trim()
                    filterStores(searchText)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            // 동적 버튼 생성 예시
            fetchStoresFromFirestore()
        }

        data class Store(
            val name: String,
            val address: String,
            // 추가적인 필드들...
        )

        private fun filterStores(searchText: String) {
            val query = firestore.collection("stores")
                .orderBy("name")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")

            query.get()
                .addOnSuccessListener { querySnapshot ->
                    val stores = querySnapshot.toObjects(Store::class.java)
                    storeAdapter.setStores(stores)
                }
                .addOnFailureListener { exception ->
                    // 실패 처리
                }
        }

        private fun fetchStoresFromFirestore() {
            firestore.collection("stores")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val stores = querySnapshot.toObjects(Store::class.java)
                    storeAdapter.setStores(stores)
                }
                .addOnFailureListener { exception ->
                    // 실패 처리
                }
        }

        // StoreAdapter 클래스 정의
        inner class StoreAdapter : RecyclerView.Adapter<SearchActivity.StoreAdapter.StoreViewHolder>() {
            private var stores: List<Store> = emptyList()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
                return StoreViewHolder(view)
            }

            override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
                val store = stores[position]
                holder.bind(store)
            }

            override fun getItemCount(): Int {
                return stores.size
            }

            fun setStores(stores: List<Store>) {
                this.stores = stores
                notifyDataSetChanged()
            }

            inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                fun bind(store: Store) {
                    // 매장 정보를 ViewHolder에 표시
                }
            }
        }
    }
