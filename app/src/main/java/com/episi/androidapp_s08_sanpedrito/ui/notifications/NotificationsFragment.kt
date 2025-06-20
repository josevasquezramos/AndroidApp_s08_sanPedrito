package com.episi.androidapp_s08_sanpedrito.ui.notifications

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.episi.androidapp_s08_sanpedrito.R
import com.episi.androidapp_s08_sanpedrito.databinding.FragmentNotificationsBinding
import com.episi.androidapp_s08_sanpedrito.databinding.ItemNotificationBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notifications = listOf(
            Notification(
                R.drawable.baseline_event_note_24,
                "Registro aprobado",
                "Tu participación en el desfile fue confirmada",
                "Hace 2 horas"
            ),
            Notification(
                R.drawable.ic_notifications_black_24dp,
                "Recordatorio",
                "El ensayo general es hoy a las 4pm en la primera puerta",
                "Ayer"
            ),
            Notification(
                R.drawable.baseline_refresh_24,
                "Cambio de horario",
                "El desfile comenzará a las 10:30am en lugar de 9:00am",
                "11 de Junio"
            )
        )

        binding.notificationsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = NotificationAdapter(notifications)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class NotificationAdapter(private val notifications: List<Notification>) :
        RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

        inner class NotificationViewHolder(val binding: ItemNotificationBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

            init {
                binding.notificationCard.setOnCreateContextMenuListener(this)
            }

            override fun onCreateContextMenu(
                menu: ContextMenu,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                activity?.menuInflater?.inflate(R.menu.notification_context, menu)
                menu.setHeaderTitle("Opciones")

                menu.findItem(R.id.action_forward).setOnMenuItemClickListener {
                    Toast.makeText(context, "Reenviar notificación ${adapterPosition}", Toast.LENGTH_SHORT).show()
                    true
                }

                menu.findItem(R.id.action_delete).setOnMenuItemClickListener {
                    Toast.makeText(context, "Eliminar notificación ${adapterPosition}", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
            val binding = ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NotificationViewHolder(binding)
        }

        override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
            val notification = notifications[position]
            with(holder.binding) {
                notificationIcon.setImageResource(notification.icon)
                notificationTitle.text = notification.title
                notificationMessage.text = notification.message
                notificationTime.text = notification.time

                notificationCard.setOnLongClickListener {
                    it.showContextMenu()
                    true
                }
            }
        }

        override fun getItemCount() = notifications.size
    }

    data class Notification(
        val icon: Int,
        val title: String,
        val message: String,
        val time: String
    )
}