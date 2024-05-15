package loveletter.src.database;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Figure {
    private JFreeChart chart;
    private String title = "Plot";
    private String xlabel = "X";
    private String ylabel = "Y";
    private XYSeriesCollection dataset;
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;
    private int width;
    private int height;

    public Figure(double[] x, double[] y, int width, int height) {
        this.width = width;
        this.height = height;
        // Create a dataset
        XYSeries series = new XYSeries("Data");
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        this.dataset = new XYSeriesCollection(series);

        // Create the chart
        this.chart = ChartFactory.createXYLineChart(this.title, this.xlabel, this.ylabel, this.dataset);
    }

    public Figure(double[] x, double[] y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Figure(double[] y) {
        this(generateXValues(y.length), y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private static double[] generateXValues(int length) {
        double[] x = new double[length];
        for (int i = 0; i < x.length; i++) {
            x[i] = i;
        }
        return x;
    }

    public void title(String title) {
        this.title = title;
        this.chart.setTitle(title);
    }

    public void xlabel(String xlabel) {
        this.xlabel = xlabel;
        this.chart = ChartFactory.createXYLineChart(this.title, this.xlabel, this.ylabel, this.dataset);
    }

    public void ylabel(String ylabel) {
        this.ylabel = ylabel;
        this.chart = ChartFactory.createXYLineChart(this.title, this.xlabel, this.ylabel, this.dataset);
    }

    public void show() {
        // Customize the plot
        this.chart.getXYPlot().setRenderer(new XYLineAndShapeRenderer());

        // Create and configure the frame
        JFrame frame = new JFrame(this.title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add chart to panel
        ChartPanel chartPanel = new ChartPanel(this.chart);
        chartPanel.setPreferredSize(new Dimension(this.width, this.height));
        frame.add(chartPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void save(String name) {
        try {
            String path = "loveletter/src/resources/plots/";
            File file = new File(path + name + ".png");
            ChartUtilities.saveChartAsPNG(file, this.chart, this.width, this.height);
            System.out.println("Chart saved as " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
