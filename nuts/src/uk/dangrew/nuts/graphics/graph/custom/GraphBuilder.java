/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import javafx.util.StringConverter;

public class GraphBuilder {
   
   static final double DEFAULT_TICK_INTERVAL = 10;
   
   private String chartTitle;
   private String xAxisTitle;
   private boolean xAxisVisible;
   private double xAxisTickInterval;
   
   private String yAxisTitle;
   private boolean yAxisVisible;
   
   private double chartXTranslation;
   private double yAxisTranslation;
   
   private StringConverter< Number > xAxisTickFormatter;

   public GraphBuilder() {
      this.xAxisVisible = true;
      this.yAxisVisible = true;
      this.xAxisTickInterval = DEFAULT_TICK_INTERVAL;
   }//End Constructor
   
   /**
    * Access to the title for the x {@link javafx.scene.chart.Axis}.
    * @return the title.
    */
   public String xAxisTitle() {
      return xAxisTitle;
   }//End Method

   /**
    * Configures the title for the x {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withXAxisTitle( String value ) {
      this.xAxisTitle = value;
      return this;
   }//End Method

   /**
    * Whether the x {@link javafx.scene.chart.Axis} should be visible on the graph.
    * @return the visibility.
    */
   public boolean isXAxisVisible() {
      return xAxisVisible;
   }//End Method

   /**
    * Configures the visibility for the x {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withXAxisVisible( boolean value ) {
      this.xAxisVisible = value;
      return this;
   }//End Method
   
   /**
    * Access to the distance between ticks for the x {@link javafx.scene.chart.Axis}.
    * @return the interval.
    */
   public double xAxisTickInterval() {
      return xAxisTickInterval;
   }//End Method

   /**
    * Configures the tick interval for the x {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withXAxisTickInterval( double value ) {
      this.xAxisTickInterval = value;
      return this;
   }//End Method

   /**
    * Access to the title for the y {@link javafx.scene.chart.Axis}.
    * @return the title.
    */
   public String yAxisTitle() {
      return yAxisTitle;
   }//End Method

   /**
    * Configures the title for the y {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withYAxisTitle( String value ) {
      this.yAxisTitle = value;
      return this;
   }//End Method

   /**
    * Whether the y {@link javafx.scene.chart.Axis} should be visible on the graph.
    * @return the visibility.
    */
   public boolean isYAxisVisible() {
      return yAxisVisible;
   }//End Method

   /**
    * Configures the visibility for the xy {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withYAxisVisible( boolean value ) {
      this.yAxisVisible = value;
      return this;
   }//End Method

   /**
    * Access to the title for the {@link javafx.scene.chart.Chart}.
    * @return the title.
    */
   public String chartTitle() {
      return chartTitle;
   }//End Method

   /**
    * Configures the title for the {@link javafx.scene.chart.Chart}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withChartTitle( String value ) {
      this.chartTitle = value;
      return this;
   }//End Method
   
   /**
    * Access to the translation for the {@link javafx.scene.chart.Chart} plot area.
    * @return the {@link javafx.scene.Node#translateXProperty()} value.
    */
   public double chartXTranslation() {
      return chartXTranslation;
   }//End Method
   
   /**
    * Configures the translation for the {@link javafx.scene.chart.Chart} plot area.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withChartXTranslation( double value ){
      this.chartXTranslation = value;
      return this;
   }//End Method
   
   /**
    * Access to the translation for the y {@link javafx.scene.chart.Axis}.
    * @return the {@link javafx.scene.Node#translateXProperty()} value.
    */
   public double yAxisTranslation() {
      return yAxisTranslation;
   }//End Method
   
   /**
    * Configures the translation for the y {@link javafx.scene.chart.Axis}.
    * @param value the value to configure.
    * @return this.
    */
   public GraphBuilder withYAxisTranslation( double value ){
      this.yAxisTranslation = value;
      return this;
   }//End Method
   
   public StringConverter< Number > xAxisTickFormatter() {
      return xAxisTickFormatter;
   }//End Method
   
   public GraphBuilder withXAxisTickFormatter( StringConverter< Number > formatter ){
      this.xAxisTickFormatter = formatter;
      return this;
   }//End Method

}//End Class
