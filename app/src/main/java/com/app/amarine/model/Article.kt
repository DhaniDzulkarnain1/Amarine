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
        imageResourceId = R.drawable.ic_article_3,
        title = "Pengelolaan Hasil Perikanan",
        description = """
            Ikan merupakan sumber protein hewani yang kaya gizi dan sangat populer di kalangan masyarakat. Namun, ikan juga memiliki kelemahan utama, yaitu cepat mengalami pembusukan setelah ditangkap. Proses ini disebabkan oleh perubahan biologis, fisik, dan kimia yang terjadi pada ikan setelah kematiannya. Oleh karena itu, pengawetan ikan menjadi penting untuk mencegah kemunduran mutu dan memperpanjang umur simpan produk perikanan.
            
            Pentingnya Pengawetan Ikan 
            Pengawetan ikan bertujuan untuk menghentikan atau memperlambat aktivitas mikroorganisme yang menyebabkan pembusukan. Dengan pengawetan yang tepat, kualitas ikan dapat dipertahankan lebih lama, sehingga dapat didistribusikan ke berbagai daerah dengan waktu penyimpanan yang lebih panjang. Selain itu, pengawetan juga memberikan nilai tambah pada produk perikanan, meningkatkan pendapatan bagi nelayan dan petambak.

            Metode Pengawetan Ikan
            Terdapat berbagai metode pengawetan ikan yang dapat digunakan untuk mencegah pembusukan, antara lain:
            • Pendinginan (Chilling): Menurunkan suhu ikan untuk memperlambat aktivitas mikroba dan enzim.
            • Pembekuan (Freezing): Menyimpan ikan pada suhu sangat rendah (-45°C) untuk menghentikan reaksi enzimatis dan aktivitas mikroorganisme.
            • Pengalengan (Canning): Mengemas ikan dalam wadah kedap udara dan memanaskannya untuk membunuh bakteri.
            • Penggaraman (Salting): Menggunakan garam untuk mengurangi kadar air dalam ikan, sehingga menghambat pertumbuhan mikroorganisme.
            • Pengeringan (Drying): Menguapkan air dari ikan untuk memperpanjang masa simpan.
            • Pengasaman (Pickling atau Marinading): Menggunakan larutan asam untuk mengawetkan ikan.
            • Pengasapan (Smoking): Memanfaatkan asap dari bahan bakar untuk memberikan rasa dan mengawetkan ikan.
            • Fermentasi: Proses biokimia yang melibatkan mikroorganisme untuk mengubah sifat ikan.
            • Pembuatan Hasil Olahan: Mengolah ikan menjadi produk lain seperti fillet atau olahan siap saji.
            • Pembuatan Hasil Sampingan: Menggunakan bagian-bagian ikan yang tidak terpakai untuk produk lain.

            Kesimpulan
            Dengan menerapkan teknik pengawetan yang tepat, kualitas dan kesegaran ikan dapat dipertahankan lebih lama. Ini tidak hanya membantu dalam menjaga pasokan makanan tetapi juga meningkatkan nilai ekonomi dari hasil tangkapan dan budidaya. Pengawetan ikan adalah langkah penting dalam industri perikanan untuk memastikan keberlanjutan dan kualitas produk yang diterima oleh konsumen.
        """.trimIndent(),
        source = "Dinas Ketahanan Pangan dan Perikanan"
    ),
    Article(
        id = 2,
        imageResourceId = R.drawable.ic_article_4,
        title = "Jenis Alat Penangkap Ikan",
        description = """
            Saat ini, alat penangkap ikan tersedia dalam berbagai jenis, mulai dari yang tradisional hingga modern. Di Indonesia, nelayan sering menggunakan kedua jenis alat ini untuk menangkap ikan. Namun, apa sebenarnya perbedaan antara keduanya? Mari kita eksplorasi lebih dalam mengenai alat penangkap ikan tradisional dan modern.
            
            Alat Penangkap Ikan Tradisional 
            Alat penangkap ikan tradisional adalah perangkat yang dioperasikan dengan cara sederhana dan telah digunakan secara turun-temurun. Terdapat beberapa jenis alat tradisional, antara lain:
            • Jala (Jaring): Terbuat dari benang dengan ukuran mata jaring yang bervariasi. Jala ini biasanya dilemparkan ke air di lokasi yang diperkirakan sebagai tempat berkumpulnya ikan. Setelah ikan terperangkap, jala diangkat dan dibawa ke darat.
            • Pancing: Alat ini umumnya terbuat dari kayu atau bambu dengan panjang sekitar 1,5 meter. Umpan diikat pada mata pancing dan dilemparkan ke area di mana ikan berkumpul.
            • Tombak (Sorangga): Tombak ini berasal dari Sulawesi Tenggara dan terbuat dari bambu kecil yang ujungnya dilengkapi dengan besi runcing. Nelayan menggunakannya dengan menusuk ikan secara langsung di dalam air.
            • Bubu: Terbuat dari bambu yang dianyam membentuk persegi dengan bagian depan berbentuk segitiga. Bubu diletakkan di dalam air untuk menangkap ikan, karena desainnya mencegah ikan keluar setelah terperangkap.

            Alat Penangkap Ikan Modern
            Alat penangkap ikan modern memanfaatkan teknologi terkini untuk meningkatkan efisiensi penangkapan. Beberapa jenis alat modern meliputi:
            • Purse Seine (Pukat Cincin): Alat ini berbentuk persegi panjang dengan cincin dan purse line yang memudahkan nelayan menangkap ikan dalam jumlah besar. Cara penggunaannya adalah dengan melingkarkan jaring di sekeliling segerombolan ikan.
            • Gillnet (Jaring Insang): Terbuat dari nilon, alat ini dirancang khusus untuk menangkap ikan berdasarkan insangnya. Pengoperasiannya melibatkan penempatan jaring di lokasi segerombolan ikan, sehingga ikan tidak dapat keluar setelah terperangkap.

            Perbedaan Antara Alat Tradisional dan Modern
            Kedua jenis alat penangkap ikan ini memiliki perbedaan signifikan dalam beberapa aspek:
            • Teknologi: Alat tradisional cenderung sederhana dan tidak memerlukan teknologi rumit, sementara alat modern membutuhkan mesin dan keahlian khusus untuk pengoperasiannya.
            • Hasil Penangkapan: Alat tradisional biasanya menghasilkan tangkapan yang lebih sedikit karena ukurannya yang kecil dan dipengaruhi oleh kondisi cuaca. Sebaliknya, alat modern dapat menangkap lebih banyak ikan tanpa terpengaruh oleh cuaca.
            • Efisiensi: Nelayan harus terus memegang alat tradisional selama proses penangkapan, sedangkan alat modern memungkinkan nelayan untuk meninggalkan alat tersebut setelah diletakkan di lokasi yang tepat, sehingga mereka dapat melakukan aktivitas lain sembari menunggu hasil tangkapan.

            Kesimpulan
            Penggunaan alat penangkap ikan tradisional dan modern memiliki karakteristik masing-masing. Teknologi yang berkembang saat ini memberikan nelayan lebih banyak pilihan dalam memilih alat yang sesuai untuk meningkatkan hasil tangkapan mereka. Dengan menggunakan alat yang tepat, nelayan dapat menangkap ikan dalam jumlah besar untuk didistribusikan ke pasar lokal maupun diekspor ke luar negeri. Untuk menjaga kualitas tinggi ikan hasil tangkapan, penting bagi nelayan untuk menyimpannya pada suhu dingin. Mesin es khusus untuk penyimpanan ikan dapat membantu menjaga kesegaran produk perikanan sebelum dijual.
        """.trimIndent(),
        source = "Dinas Perikanan dan Kelautan"
    ),
    Article(
        id = 3,
        imageResourceId = R.drawable.ic_article_5,
        title = "Cara Penanganan Ikan Yang Baik",
        description = """
        Ikan merupakan komoditas yang memiliki nilai ekonomis tinggi, namun ia sangat rentan mengalami penurunan mutu yang cepat. Oleh karena itu, perlu perhatian serius untuk menjaga mutu ikan. Berikut adalah panduan lengkap tentang cara penanganan ikan yang baik, yang disingkat sebagai CPIB (Cara Penanganan Ikan yang Baik).

        Komponen Sistem Perikanan
        Perikanan merupakan sebuah sistem yang terdiri dari tiga komponen utama: produksi, penanganan, dan pengolahan serta pemasaran. Tahap akhir dari kegiatan perikanan adalah penanganan, yang sangat kritikal untuk menghambat penurunan kualitas ikan. Penanganan harus dilakukan segera setelah ikan ditangkap atau dipanen untuk memaksimalkan kualitasnya.

        A. Penanganan Ikan di Atas Kapal
        1. Ganco dan Mendaratkan Ikan di Atas Kapal
        • Gunakan ganco pada bagian kepala ikan dekat insang. Jika ukuran ikan agak besar, gunakan ganco tambahan pada bagian mulut.
        • Letakkan ikan di atas kapal secara hati-hati dengan posisi menyamping untuk mempermudah penanganan selanjutnya. Hindari ganco mengenai jantung ikan, karena jantung harus tetap berdetak ketika proses pengeluaran darah.

        2. Mematikan Ikan
        • Positikan ikan menyamping.
        • Pingsankan ikan dengan memukul titik tepat di antara mata (otak kecil). Pukulan pada titik yang tepat akan mematikan ikan meskipun pukulan tidak terlalu keras.
        • Pastikan ikan mati dengan mengusap mata atau menggerakkan rahang bagian bawah untuk memeriksa respon ikan. Ikan yang mati cepat akan bertahan lebih lama dibandingkan ikan yang lama meronta-ronta. Ikan yang mati ditusuk otak kecil akan lebih cepat didinginkan karena otak yang mengatur suhu tubuh ikan telah dirusak.

        3. Pendarahan
        • Tusuk bagian bawah sirip dada dengan kedalaman 2 cm pada kedua sisi. Darah hangat akan mengucur deras apabila titik penusukan benar.
        • Buat irisan pada pangkal ekor pada kedua sisi. Semakin banyak darah yang dikeluarkan, semakin baik mutu ikan yang dipertahankan. Pendarahan akan menurunkan suhu ikan lebih cepat.

        4. Penyiangan
        • Buat sobekan kecil pada perut dekat anus, lalu potong saluran pencernaan/gonad menuju anus.
        • Lepaskan insang dengan mengiris membrane pada pinggiran insang, lalu potong bagian yang menghubungkan insang dengan rahang maupun tengkorak.
        • Angkat insang bersama isi perut ikan, lalu bersihkan sisa darah dengan bantuan sikat dan air bersih. Insang dan isi perut merupakan sumber bakteri paling besar pada ikan (selain kulit), sehingga harus dibuang.

        5. Penyimpanan
        • Simpan ikan dalam box fiber/sterofoam atau palka berinsulasi dengan tambahan es. Perbandingan jumlah es dan ikan adalah 1:1. Seluruh permukaan ikan harus tertutupi dengan es. Apabila ikan disusun bertumpuk, susunannya adalah es-ikan-es-ikan-es.
        • Gunakan es curah karena kontak dengan tubuh ikan secara merata, menurunkan suhu ikan dengan cepat.

        B. Penanganan Ikan di TPI (Tempat Pendaratan Ikan)
        Transaksi Ikan Pertama kali di TPI dilakukan langsung setelah kapal penangkap ikan beroperasi. Syarat TPI melibatkan persediaan air bersih, tempat penyimpanan es, wadah tempat melelang ikan, dan lantai mudah dibersihkan tanpa genangan.

        Proses Pembongkaran Hasil Tangkapan
        1. Mengeluarkan Air dari Wadah Berisi Ikan
        2. Ikan Dipindahkan Ke Dalam Keranjang
        3. Ikan Dicuci dengan Menyemprotkan Air Laut Dingin
        4. Diberi Es Lalu Dibawa Ke TPI Secara Tertutup

        Di TPI, ikan langsung dipindahkan ke wadah pelelangan atau bahkan ikan langsung ditumpahkan ke lantai pelelangan. Pelelangan terjadi pagi/sore hari untuk menghindari panas terlalu tinggi. Selama pemajangan, ikan selalu disiram air dingin agar suhu ikan tetap dingin dan lendir hilang. Ikan yang dipajang di etalase didinginkan dengan cara meletakkan es dalam ruang etalase atau kombinasi udara dingin dan es. Jumlah ikan yang tidak terjual secepatnya disimpan kembali dalam wadah ber-es dan garam 2,5% dari berat es agar dapat terjual esok harinya.

        Manfaat Penanganan Ikan yang Baik adalah mampu mempertahankan kualitas ikan dalam waktu yang lebih lama, menyingkat waktu penanganan karena menggunakan teori dan metode yang benar, serta menghasilkan kualitas ikan yang memiliki mutu yang lebih baik.

        Prinsip-prinsip dalam penanganan ikan melibatkan 3 C + Q, yaitu CLEAN (bersih), CAREFULL (hati-hati), COOL CHAIN (rantai dingin), dan QUICK (cepat).

        Dengan demikian, penanganan ikan yang tepat sangat penting untuk menjaga kesegaran dan kualitas produk perikanan sehingga dapat dinikmati oleh konsumen secara optimal.
    """.trimIndent(),
        source = "Dinas Perikanan"
    )
)
