package com.example.nammarastehealth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            NammaRasteDashboard()
        }
    }
}

@Composable
fun NammaRasteDashboard() {

    val context = LocalContext.current

    var searchRoad by remember { mutableStateOf("") }

    var reportCount by remember { mutableStateOf(8) }

    val timestamp =
        SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss",
            Locale.getDefault()
        ).format(Date())

    val roadList = listOf(
        "Mysuru Main Road",
        "PMGSY Rural Route",
        "Hassan Village Road",
        "Mandya Smart Corridor",
        "Tumkur PMGSY Road",
        "Kodagu Hill Route",
        "Ramanagara Link Road"
    )

    val filteredRoads =
        roadList.filter {
            it.contains(searchRoad, ignoreCase = true)
        }

    val healthStatus =
        when {
            reportCount <= 5 -> "GOOD"
            reportCount <= 10 -> "MODERATE"
            else -> "CRITICAL"
        }

    val healthColor =
        when (healthStatus) {
            "GOOD" -> Color(0xFF00C853)
            "MODERATE" -> Color(0xFFFF9800)
            else -> Color.Red
        }

    val healthScore =
        when (healthStatus) {
            "GOOD" -> 90
            "MODERATE" -> 58
            else -> 25
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2F8))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "🚧 Taluka Road Dashboard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )

        Spacer(modifier = Modifier.height(18.dp))

        /*
        DASHBOARD
         */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            DashboardCard("Roads", "124")
            DashboardCard("Reports", "$reportCount")
            DashboardCard("Healthy", "89")
        }

        Spacer(modifier = Modifier.height(20.dp))

        /*
        ROAD DIRECTORY
         */

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "🔎 Road Directory",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = searchRoad,
                    onValueChange = {
                        searchRoad = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Search Road Name")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {

                        Toast.makeText(
                            context,
                            "Road Search Working",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0)
                    )
                ) {

                    Text(
                        text = "SEARCH ROAD",
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                filteredRoads.forEach {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        )
                    ) {

                        Text(
                            text = "• $it",
                            modifier = Modifier.padding(14.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        /*
        DAMAGE REPORTING
         */

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "📸 Damage Reporting",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(14.dp))

                Image(
                    painter = rememberAsyncImagePainter(
                        "https://images.unsplash.com/photo-1516321318423-f06f85e504b3"
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Timestamp: $timestamp",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "📍 GPS Location Captured",
                    color = Color(0xFF00C853),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    /*
                    CAMERA BUTTON
                     */

                    Button(
                        onClick = {

                            try {

                                val intent =
                                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                                context.startActivity(intent)

                            } catch (e: Exception) {

                                try {

                                    val fallbackIntent =
                                        Intent(Intent.ACTION_VIEW)

                                    fallbackIntent.setData(
                                        Uri.parse(
                                            "content://media/internal/images/media"
                                        )
                                    )

                                    context.startActivity(fallbackIntent)

                                } catch (e: Exception) {

                                    Toast.makeText(
                                        context,
                                        "No Camera App Found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0)
                        )
                    ) {

                        Text(
                            text = "OPEN CAMERA",
                            color = Color.White
                        )
                    }

                    /*
                    REPORT BUTTON
                     */

                    Button(
                        onClick = {

                            reportCount++

                            Toast.makeText(
                                context,
                                "Damage Report Submitted",
                                Toast.LENGTH_SHORT
                            ).show()

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {

                        Text(
                            text = "REPORT DAMAGE",
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Reports In 1KM Stretch: $reportCount",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        /*
        ROAD HEALTH ANALYTICS
         */

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "📊 Road Health Analytics",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ROAD CONDITION: $healthStatus",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = healthColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { healthScore / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(50)),
                    color = healthColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Road Health Score: $healthScore%",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Pothole Reports: ${reportCount / 2}",
                    color = Color.Black
                )

                Text(
                    text = "Water Logging Reports: ${reportCount / 3}",
                    color = Color.Black
                )

                Text(
                    text = "Crack Reports: ${reportCount / 4}",
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Road health changes dynamically based on reports in a 1KM stretch.",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        /*
        CONTRACTOR INFO
         */

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "👷 Contractor Information",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Contractor: Karnataka Infra Pvt Ltd",
                    color = Color.Black
                )

                Text(
                    text = "Warranty Period: 5 Years",
                    color = Color.Black
                )

                Text(
                    text = "Road Length: 12 KM",
                    color = Color.Black
                )

                Text(
                    text = "Contact: +91 9876543210",
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = rememberAsyncImagePainter(
                        "https://upload.wikimedia.org/wikipedia/commons/6/69/Simple_world_map.png"
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {

                        try {

                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://maps.google.com")
                            )

                            context.startActivity(intent)

                        } catch (e: Exception) {

                            Toast.makeText(
                                context,
                                "Google Maps Not Found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {

                    Text(
                        text = "OPEN ROAD MAP",
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        /*
        BEST MAINTAINED ROADS
         */

        Text(
            text = "🏆 Best Maintained Roads",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D47A1)
        )

        Spacer(modifier = Modifier.height(12.dp))

        BestRoadCard("Mandya Smart Road")
        BestRoadCard("Hassan Green Corridor")
        BestRoadCard("Tumkur PMGSY Route")
        BestRoadCard("Kodagu Eco Highway")
        BestRoadCard("Mysuru Smart Highway")

        Spacer(modifier = Modifier.height(24.dp))

        /*
        IMPACT GOALS
         */

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "🌱 Impact Goals",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "• Protect long-term public road investments",
                    color = Color.Black
                )

                Text(
                    text = "• Improve contractor transparency",
                    color = Color.Black
                )

                Text(
                    text = "• Reduce rural transportation problems",
                    color = Color.Black
                )

                Text(
                    text = "• Enable smart infrastructure monitoring",
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun DashboardCard(
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.width(105.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1565C0)
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = title,
                color = Color.White
            )
        }
    }
}

@Composable
fun BestRoadCard(name: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Text(
                text = "Excellent Maintenance Score",
                color = Color.Gray
            )
        }
    }
}