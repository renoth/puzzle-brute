package de.ninjo.puzzlebrute;

import de.ninjo.puzzlebrute.domain.Hexfield;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DrawSolutions {
    public static void main(String args[]) throws IOException {
        int width = 640, height = 640;

        long fileCount = Files.walk(Paths.get("target"), 1)
                .filter(file -> Files.isRegularFile(file)).count();

        BufferedImage bi = new BufferedImage(width * 2, (int) (height * fileCount / 2) + height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.white);
        g.fillRect(0, 0, width * 2, (int) (height * fileCount / 2) + height);

        final int[] iteration = {1};

        Files.walk(Paths.get("target"), 1)
                .filter(file -> Files.isRegularFile(file))
                .forEach(path -> {
                    List<Hexfield> hexfieldList = new ArrayList<>();

                    try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] s = line.split(",");
                            hexfieldList.add(new Hexfield(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int xOffset = (iteration[0] % 2 == 0) ? width : 0;
                    int yOffset = ((iteration[0] - 1) / 2) * height;

                    g.setFont(new Font("Arial", Font.BOLD, 24));
                    g.drawString(iteration[0] + "", xOffset, yOffset + 50);

                    System.out.println(iteration[0] + " = " + path.getFileName());

                    for (Hexfield hex : hexfieldList) {
                        if (hex.getId() == 13) {
                            g.setColor(Color.magenta);
                        } else {
                            g.setColor(new Color(hex.getId() * 19, Math.abs(255 - hex.getId() * 38), 255 - hex.getId() * 19));
                        }

                        int xPos = (int) Math.round(hex.getXYPosition().getX()) + xOffset;
                        int yPos = (int) Math.round(hex.getXYPosition().getY()) + yOffset;

                        g.fillPolygon(getHexagon(xPos + width / 2, yPos + height / 2, 40));

                        g.setColor(Color.BLACK);
                        g.drawString(hex.getId() + "", xPos + (width / 2) - 5, yPos + (height / 2) - 10);
                    }

                    iteration[0]++;
                });

        try {
            ImageIO.write(bi, "PNG", new File("solutions.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Polygon getHexagon(int x, int y, int h) {
        Polygon hexagon = new Polygon();

        double a;
        for (int i = 0; i < 7; i++) {
            a = Math.PI / 3.0 * i + Math.PI / 2.0;
            hexagon.addPoint((int) (Math.round(x + Math.sin(a) * h)), (int) (Math.round(y + Math.cos(a) * h)));
        }
        return hexagon;
    }
}
