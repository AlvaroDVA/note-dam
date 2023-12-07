import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.ad.notedam.Adapters.ItemOnClickListener
import com.dam.ad.notedam.Adapters.SubListAdapter
import com.dam.ad.notedam.Models.nota.*
import com.dam.ad.notedam.R
import com.dam.ad.notedam.databinding.ItemNotaImagenBinding
import com.dam.ad.notedam.databinding.ItemNotaListaBinding
import com.dam.ad.notedam.databinding.ItemNotaTextoBinding
import com.dam.ad.notedam.utils.MainContext
import java.util.*

class ItemNotaAdapter(
    private var listItem: MutableList<Nota>,
    private var listener: ItemOnClickListener<Nota>
) : RecyclerView.Adapter<ItemNotaAdapter.ViewHolder>()  {

    // Tipos de vistas
    private val TIPO_TEXTO = 1
    private val TIPO_IMAGEN = 2
    private val TIPO_LISTA = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TIPO_TEXTO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_nota_texto, parent, false)
                TextoViewHolder(view)
            }
            TIPO_IMAGEN -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_nota_imagen, parent, false)
                ImagenViewHolder(view)
            }
            TIPO_LISTA -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_nota_lista, parent, false)
                ListaViewHolder(view)
            }
            else -> throw IllegalArgumentException("Tipo de vista desconocido")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nota = listItem[position]
        holder.bind(nota)
        holder.setListener(nota)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            is NotaTexto -> TIPO_TEXTO
            is NotaLista -> TIPO_LISTA
            is NotaImagen -> TIPO_IMAGEN
        }
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(listItem, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        cambiarPrioridades()
    }

    private fun cambiarPrioridades() {
        var contador = 0
        listItem.forEach {
            it.prioridad = contador
            contador++
        }
    }

    // ViewHolder para notas de texto
    inner class TextoViewHolder(view: View) : ViewHolder(view) {
        val binding = ItemNotaTextoBinding.bind(view)
        override fun bind(nota: Nota) {
            val notaCambiada = nota as NotaTexto
            binding.textoNota.text = notaCambiada.textoNota
            binding.deleteButton.setImageResource(R.drawable.baseline_delete_24)
        }

        override fun setListener(nota: Nota) {
            binding.root.apply {
                setOnClickListener {
                    listener.onClick(nota.uuidNota)
                }
                setOnLongClickListener {
                    listener.onLongClickListener(nota.uuidNota)
                }
            }
        }

    }

    // ViewHolder para notas de imagen
    inner class ImagenViewHolder(view: View) : ViewHolder(view) {
        val binding = ItemNotaImagenBinding.bind(view)
        override fun bind(nota: Nota) {
            val notaImagen = nota as NotaImagen
            notaImagen.uriImagen?.let {
                binding.imagenNota.setImageURI(it)
            }?:let {
                binding.imagenNota.setImageResource(R.mipmap.img_default_layout)
            }
            binding.textoNotaImagen.text = notaImagen.textoNota
        }

        override fun setListener(nota: Nota) {
            binding.root.apply {
                setOnClickListener {
                    listener.onClick(nota.uuidNota)
                }
                setOnLongClickListener {
                    listener.onLongClickListener(nota.uuidNota)
                }
            }
        }
    }

    inner class ListaViewHolder (view : View) : ViewHolder(view) {
        private val binding = ItemNotaListaBinding.bind(view)

        override fun bind(nota: Nota) {
            val notaLista = nota as NotaLista
            binding.textoNotaLista.text = notaLista.textoNota
            val adapter = SubListAdapter(
                listItem = notaLista.lista
            )
            val mLayoutManager = LinearLayoutManager(MainContext.mainActivity)

            binding.recyclerViewLista.apply {
                this.adapter = adapter
                this.layoutManager = mLayoutManager
            }
        }

        override fun setListener(nota: Nota) {
            binding.root.apply {
                setOnClickListener {
                    listener.onClick(nota.uuidNota)
                }
                setOnLongClickListener {
                    listener.onLongClickListener(nota.uuidNota)
                }
            }
        }


    }

    // Clase base ViewHolder
    abstract inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(nota: Nota)
        abstract fun setListener(nota : Nota)
    }


}