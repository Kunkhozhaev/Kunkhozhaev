package uz.texnopos.labworkapp.core

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.texnopos.labworkapp.R

open class AppBaseActivity : AppCompatActivity() {
    var showing = true
    private var progressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(0))
            progressDialog?.setContentView(R.layout.dialog_progress)
        }
    }

    fun showProgress(show: Boolean) {
        if (progressDialog != null) {
            when {
                show -> {
                    if (!isFinishing && !progressDialog!!.isShowing) {
                        progressDialog?.setCanceledOnTouchOutside(false)
                        progressDialog?.setCancelable(false)
                        progressDialog?.show()
                        showing = false
                    }
                }
                else -> try {
                    if (progressDialog?.isShowing!! && !isFinishing) {
                        progressDialog?.dismiss()
                        showing = true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}