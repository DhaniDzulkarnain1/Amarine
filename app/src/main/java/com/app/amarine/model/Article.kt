package com.app.amarine.model

import android.os.Parcelable
import com.app.amarine.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val imageResourceId: Int,
    val title: String,
    val description: String,
    val source: String,
    var isFavorite: Boolean = false,
) : Parcelable

val articles = listOf(
    Article(
        id = 0,
        imageResourceId = R.drawable.ic_article_1,
        title = "Pengolahan Hasil Perikanan",
        description = "Ikan merupakan sumber protein hewani yang kaya gizi dan sangat populer di kalangan masyarakat. Namun, ikan juga memiliki kelemahan utama, yaitu cepat mengalami pembusukan setelah ditangkap. Proses ini disebabkan oleh perubahan biologis, fisik, dan kimia yang terjadi pada ikan setelah kematiannya. Oleh karena itu, pengawetan ikan menjadi penting untuk mencegah kemunduran mutu dan memperpanjang umur simpan produk perikanan. \n\nPentingnya Pengawetan Ikan \n" +
                "Pengawetan ikan bertujuan untuk menghentikan atau memperlambat aktivitas mikroorganisme yang menyebabkan pembusukan. Dengan pengawetan yang tepat, kualitas ikan dapat dipertahankan lebih lama, sehingga dapat didistribusikan ke berbagai daerah dengan waktu penyimpanan yang lebih panjang. Selain itu, pengawetan juga memberikan nilai tambah pada produk perikanan, meningkatkan pendapatan bagi nelayan dan petambak.\nMetode Pengawetan Ikan\n" +
                "Terdapat berbagai metode pengawetan ikan yang dapat digunakan untuk mencegah pembusukan, antara lain:\n" +
                "•\tPendinginan (Chilling): Menurunkan suhu ikan untuk memperlambat aktivitas mikroba dan enzim.\n" +
                "•\tPembekuan (Freezing): Menyimpan ikan pada suhu sangat rendah (-45°C) untuk menghentikan reaksi enzimatis dan aktivitas mikroorganisme\n" +
                "•\tPengalengan (Canning): Mengemas ikan dalam wadah kedap udara dan memanaskannya untuk membunuh bakteri.\n" +
                "•\tPenggaraman (Salting): Menggunakan garam untuk mengurangi kadar air dalam ikan, sehingga menghambat pertumbuhan mikroorganisme.\n" +
                "•\tPengeringan (Drying): Menguapkan air dari ikan untuk memperpanjang masa simpan.\n" +
                "•\tPengasaman (Pickling atau Marinading): Menggunakan larutan asam untuk mengawetkan ikan.\n" +
                "•\tPengasapan (Smoking): Memanfaatkan asap dari bahan bakar untuk memberikan rasa dan mengawetkan ikan.\n" +
                "•\tFermentasi: Proses biokimia yang melibatkan mikroorganisme untuk mengubah sifat ikan.\n" +
                "•\tPembuatan Hasil Olahan: Mengolah ikan menjadi produk lain seperti fillet atau olahan siap saji.\n" +
                "•\tPembuatan Hasil Sampingan: Menggunakan bagian-bagian ikan yang tidak terpakai untuk produk lain.\n\nKesimpulan\n" +
                "Dengan menerapkan teknik pengawetan yang tepat, kualitas dan kesegaran ikan dapat dipertahankan lebih lama. Ini tidak hanya membantu dalam menjaga pasokan makanan tetapi juga meningkatkan nilai ekonomi dari hasil tangkapan dan budidaya. Pengawetan ikan adalah langkah penting dalam industri perikanan untuk memastikan keberlanjutan dan kualitas produk yang diterima oleh konsumen.\n",
        source = "Kementerian Kelautan dan Perikanan",
    ),
    Article(
        id = 2,
        imageResourceId = R.drawable.ic_article_2,
        title = "Cara Penyimpanan Ikan Hasil Tangkapan",
        description = "Saat ini, alat penangkap ikan tersedia dalam berbagai jenis, mulai dari yang tradisional hingga modern. Di Indonesia, nelayan sering menggunakan kedua jenis alat ini untuk menangkap ikan. Namun, apa sebenarnya perbedaan antara keduanya? Mari kita eksplorasi lebih dalam mengenai alat penangkap ikan tradisional dan modern.\n\nAlat Penangkap Ikan Tradisional\n" +
                "Alat penangkap ikan tradisional adalah perangkat yang dioperasikan dengan cara sederhana dan telah digunakan secara turun-temurun. Terdapat beberapa jenis alat tradisional, antara lain:\n" +
                "•\tJala (Jaring): Terbuat dari benang dengan ukuran mata jaring yang bervariasi. Jala ini biasanya dilemparkan ke air di lokasi yang diperkirakan sebagai tempat berkumpulnya ikan. Setelah ikan terperangkap, jala diangkat dan dibawa ke darat.\n" +
                "•\tPancing: Alat ini umumnya terbuat dari kayu atau bambu dengan panjang sekitar 1,5 meter. Umpan diikat pada mata pancing dan dilemparkan ke area di mana ikan berkumpul.\n" +
                "•\tTombak (Sorangga): Tombak ini berasal dari Sulawesi Tenggara dan terbuat dari bambu kecil yang ujungnya dilengkapi dengan besi runcing. Nelayan menggunakannya dengan menusuk ikan secara langsung di dalam air.\n" +
                "•\tBubu: Terbuat dari bambu yang dianyam membentuk persegi dengan bagian depan berbentuk segitiga. Bubu diletakkan di dalam air untuk menangkap ikan, karena desainnya mencegah ikan keluar setelah terperangkap.\n\nAlat Penangkap Ikan Modern\n" +
                "Alat penangkap ikan modern memanfaatkan teknologi terkini untuk meningkatkan efisiensi penangkapan. Beberapa jenis alat modern meliputi:\n" +
                "•\tPurse Seine (Pukat Cincin): Alat ini berbentuk persegi panjang dengan cincin dan purse line yang memudahkan nelayan menangkap ikan dalam jumlah besar. Cara penggunaannya adalah dengan melingkarkan jaring di sekeliling segerombolan ikan.\n" +
                "•\tGillnet (Jaring Insang): Terbuat dari nilon, alat ini dirancang khusus untuk menangkap ikan berdasarkan insangnya. Pengoperasiannya melibatkan penempatan jaring di lokasi segerombolan ikan, sehingga ikan tidak dapat keluar setelah terperangkap.\n\nPerbedaan Antara Alat Tradisional dan Modern\n" +
                "Kedua jenis alat penangkap ikan ini memiliki perbedaan signifikan dalam beberapa aspek:\n" +
                "•\tTeknologi: Alat tradisional cenderung sederhana dan tidak memerlukan teknologi rumit, sementara alat modern membutuhkan mesin dan keahlian khusus untuk pengoperasiannya.\n" +
                "•\tHasil Penangkapan: Alat tradisional biasanya menghasilkan tangkapan yang lebih sedikit karena ukurannya yang kecil dan dipengaruhi oleh kondisi cuaca. Sebaliknya, alat modern dapat menangkap lebih banyak ikan tanpa terpengaruh oleh cuaca.\n" +
                "•\tEfisiensi: Nelayan harus terus memegang alat tradisional selama proses penangkapan, sedangkan alat modern memungkinkan nelayan untuk meninggalkan alat tersebut setelah diletakkan di lokasi yang tepat, sehingga mereka dapat melakukan aktivitas lain sembari menunggu hasil tangkapan.\n\nKesimpulan\n" +
                "Penggunaan alat penangkap ikan tradisional dan modern memiliki karakteristik masing-masing. Teknologi yang berkembang saat ini memberikan nelayan lebih banyak pilihan dalam memilih alat yang sesuai untuk meningkatkan hasil tangkapan mereka. Dengan menggunakan alat yang tepat, nelayan dapat menangkap ikan dalam jumlah besar untuk didistribusikan ke pasar lokal maupun diekspor ke luar negeri.Untuk menjaga kualitas tinggi ikan hasil tangkapan, penting bagi nelayan untuk menyimpannya pada suhu dingin. Mesin es khusus untuk penyimpanan ikan dapat membantu menjaga kesegaran produk perikanan sebelum dijual. Jika Anda memerlukan solusi penyimpanan es berkualitas tinggi, Anda dapat menghubungi PND Ice sebagai penyedia mesin es terbaik.\n",
        source = "Buku Panduan FAO",
    ),
    Article(
        id = 3,
        imageResourceId = R.drawable.ic_article_1,
        title = "Pengolahan Hasil Perikanan",
        description = "Ikan merupakan komoditas yang memiliki nilai ekonomis tinggi, namun ia sangat rentan mengalami penurunan mutu yang cepat. Oleh karena itu, perlu perhatian serius untuk menjaga mutu ikan. Berikut adalah panduan lengkap tentang cara penanganan ikan yang baik, yang disingkat sebagai CPIB (Cara Penanganan Ikan yang Baik).\n\nKomponen Sistem Perikanan\n" +
                "Perikanan merupakan sebuah sistem yang terdiri dari tiga komponen utama: produksi, penanganan, dan pengolahan serta pemasaran. Tahap akhir dari kegiatan perikanan adalah penanganan, yang sangat kritikal untuk menghambat penurunan kualitas ikan. Penanganan harus dilakukan segera setelah ikan ditangkap atau dipanen untuk memaksimalkan kualitasnya.\n",
        source = "Kementerian Kelautan dan Perikanan",
    ),
)