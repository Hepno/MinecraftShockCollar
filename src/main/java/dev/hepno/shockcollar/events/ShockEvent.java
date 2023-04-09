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
                String command = "curl -d '{\"Username\":\"" + username + "\",\"Name\":\"" + name + "\",\"Code\":\"" + code + "\",\"Intensity\":\"" + intensity + "\",\"Duration\":\"" + duration + "\",\"Apikey\":\"" + apikey + "\",\"Op\":\"0\"}' -H 'Content-Type: application/json' https://do.pishock.com/api/apioperate";
                ProcessBuilder pb = new ProcessBuilder(command.split("\\s+"));
                pb.redirectErrorStream(true);
                Process process = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
