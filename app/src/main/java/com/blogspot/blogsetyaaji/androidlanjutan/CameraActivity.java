package com.blogspot.blogsetyaaji.androidlanjutan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        return Uri.fromFile(getOutputNamaFile(fotoCode));
        //return FileProvider.getUriForFile(CameraActivity.this,
                //BuildConfig.APPLICATION_ID + ".provider",
                //getOutputNamaFile(fotoCode));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // ambil data sesuai request code yang diberikan
        if (requestCode == REQUEST_CODE_CAMERA){
            // tampilkan foto di imageview
            tampilFoto();
        } else if (resultCode == RESULT_CANCELED){
            // jika user batal memilih aplikasi camera
            Toast.makeText(this, "Batal mengambil foto", Toast.LENGTH_SHORT).show();
        } else {
            // jika gagal menampilkan gambar/gagal menampilkan aplikasi camera
            Toast.makeText(this, "Gagal mengambil foto", Toast.LENGTH_SHORT).show();
        }
    }

    private void tampilFoto() {
        // ambil lokasi foto
        Uri fotoUri = Uri.parse(pathFoto);
        // ambil file
        File file = new File(fotoUri.getPath());

        // cek apakah file ada
        if (file.exists()) {
            // jika dile ditemukan maka ambil file
            try {
                InputStream fileFoto = new FileInputStream(file);
                // tampilkan foto di image view
                imgFoto.setImageBitmap(BitmapFactory.decodeStream(fileFoto));
            } catch (FileNotFoundException e) {
                // jika percobaan gagal
                Toast.makeText(this, "Failed to get file", Toast.LENGTH_SHORT).show();
            }
        } else {
            // jika file tidak ditemukan
            imgFoto.setImageResource(0);
            pathFoto = null;
            Toast.makeText(this, "Foto tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // tampilkan menu pada action bar
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // tampung id menunya
        int id = item.getItemId();

        // jika yang dipilih adalah menu share
        if (id == R.id.mn_share) {
            // cek keberadaan gambar
            if (pathFoto == null){
                Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
                return false;
            } else if (imgFoto.getDrawable() == null){
                Toast.makeText(this, "Foto belum tersedia", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                // berikan lokasi foto ke intent
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                // tampilkan aplikasi yang akan dikirim foto
                startActivity(intent.createChooser(intent, "Share photo"));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
