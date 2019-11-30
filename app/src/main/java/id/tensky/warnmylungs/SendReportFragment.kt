package id.tensky.warnmylungs


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_send_report.view.*

/**
 * A simple [Fragment] subclass.
 */
class SendReportFragment : Fragment() {
    lateinit var root:View
    val REQUEST_FOTO_CODE = 1
    val REQUEST_CAMERA_PERMISSION = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_send_report, container, false)
        root.report_add_image.setOnClickListener {
            requestCameraPermission()
        }

        return root
    }

    private fun getContent(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_FOTO_CODE)
    }

    private fun requestCameraPermission(){
        if(ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }else{
            getContent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_FOTO_CODE && resultCode == Activity.RESULT_OK){
            Glide.with(context!!).load(data?.extras?.get("data")).apply(RequestOptions.circleCropTransform()).into(root.report_add_image)
        }else if (requestCode == REQUEST_CAMERA_PERMISSION && resultCode == Activity.RESULT_OK){
            requestCameraPermission()
        }
    }


}
