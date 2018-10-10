package com.blogspot.blogsetyaaji.androidlanjutan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.imgFoto)
    ImageView imgFoto;

    // lokasi gambar dalam directory
    private String pathFoto;
    // nama folder
    private static final String folderFoto = "Aplikasi Kameraku";

    // komponen untuk mengambil lokasi foto dalam directory
    private Uri fileUri;

    // penampung requestCode untuk membuka aplikasi kamera
    private static final int REQUEST_CODE_CAMERA = 4;
    // kode foto yang dibuat
    private static final int FOTO_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // minta izin mengakses camera
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 14);
        }

        // cek camera di perangkat
        if (!supportCamera()) {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    getString(R.string.camera_error),
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ambil foto
                ambilFoto();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ambilFoto() {
        // memanggil aplikasi kamara menggunakan intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ambil directori penyimpanan foto dari method pengaturan folder penyimpanan
        fileUri = getOutputFileUri(FOTO_CODE);
        // kirim directori yang telah diambil ke aplikasi foto yang akan dipanggil
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // eksekusi apliaksi
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private Uri getOutputFileUri(int fotoCode) {
        // mengambil direktori file berupa URI
        return FileProvider.getUriForFile(CameraActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                getOutputNamaFile(fotoCode));
    }

    private File getOutputNamaFile(int fotoCode) {
        // atur folder penyimpanan foto ke (SDCard/Picture/folder_foto)
        File penyimpananMedia = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ), folderFoto);
        // tmapilkan alamat file di log
        Log.d("Direcotry File Foto", penyimpananMedia.getAbsolutePath());

        // cek apakah foldernya sudah ada atau belum
        if (!penyimpananMedia.exists()) {
            // buat folder menggunakan mkdir()
            // jika gagal membuat folder baru, tampilkna pesan error
            if (!penyimpananMedia.mkdir()) {
                Toast.makeText(this, getString(R.string.dir_error),
                        Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        // membuat format waktu untuk file foto
        String waktuFoto = new SimpleDateFormat("yyyyMMdd_hhMMss", Locale.getDefault())
                .format(new Date());
        // tampilkan hasil waktunnya di logcat
        Log.d("Waktu Foto", waktuFoto);

        File namaFile;
        if (fotoCode == FOTO_CODE) {
            // pasang nama file dengan waktu yang sudah dibuat sebelumnya
            namaFile = new File((penyimpananMedia.getPath() + File.separator
                    + "IMG" + waktuFoto + ".jpg"));
            // tampilkan nama file di logcat
            Log.d("Nama File Foto", namaFile.getAbsolutePath());
        } else {
            return null;
        }

        // atur path/alamat foto guna menampikan foto di imageview
        pathFoto = "file:" + namaFile.getAbsolutePath();
        // tampilkan path foto di log
        Log.d("Path Foto", pathFoto);

        // kembalkan data namaFile
        return namaFile;
    }

    private boolean supportCamera() {
        // cek apakah kamera pada perangkat dalam kondisi baik
        if (getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    // TODO: 10/10/2018 menampilkan gambar ke imageview

}
