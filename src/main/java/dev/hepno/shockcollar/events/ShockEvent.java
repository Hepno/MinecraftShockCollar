package dev.hepno.shockcollar.events;

import dev.hepno.shockcollar.ShockCollar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShockEvent implements Listener {

    private final ShockCollar plugin;

    public ShockEvent(ShockCollar plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShock(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            String username = plugin.getConfig().getString("credentials.username");
            String apikey = plugin.getConfig().getString("credentials.apikey");
            String code = plugin.getConfig().getString("credentials.code");
            String name = plugin.getConfig().getString("credentials.name");
            String duration =  plugin.getConfig().getString("credentials.duration");
            String intensity = plugin.getConfig().getString("credentials.intensity");

            try {
                URL url = new URL("https://do.pishock.com/api/apioperate");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String postData = "{\"Username\":\"" + username + "\",\"Name\":\"" + name + "\",\"Code\":\"" + code + "\",\"Intensity\":\"" + intensity + "\",\"Duration\":\"" + duration + "\",\"Apikey\":\"" + apikey + "\",\"Op\":\"0\"}";
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getLogger().info("Ouch!");
        }
    }
}
