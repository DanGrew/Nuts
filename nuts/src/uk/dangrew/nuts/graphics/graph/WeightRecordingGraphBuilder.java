/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

/**
 * {@link WeightRecordingGraphBuilder} provides a builder for configuring a {@link WeightRecordingGraph}.
 */
public class WeightRecordingGraphBuilder {
   
   static final double DEFAULT_TICK_INTERVAL = 10;
   
   private String chartTitle;
   private String xAxisTitle;
   private boolean xAxisVisible;
   private double xAxisTickInterval;
   
   private String yAxisTitle;
   private boolean yAxisVisible;
   
   private double chartXTranslation;
   private double yAxisTranslation;

   /**
    * Constructs a new {@link WeightRecordingGraphBuilder}.
    */
   public WeightRecordingGraphBuilder() {
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
   public WeightRecordingGraphBuilder withXAxisTitle( String value ) {
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
   public WeightRecordingGraphBuilder withXAxisVisible( boolean value ) {
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
   public WeightRecordingGraphBuilder withXAxisTickInterval( double value ) {
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
   public WeightRecordingGraphBuilder withYAxisTitle( String value ) {
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
   public WeightRecordingGraphBuilder withYAxisVisible( boolean value ) {
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
   public WeightRecordingGraphBuilder withChartTitle( String value ) {
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
   public WeightRecordingGraphBuilder withChartXTranslation( double value ){
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
   public WeightRecordingGraphBuilder withYAxisTranslation( double value ){
      this.yAxisTranslation = value;
      return this;
   }//End Method

}//End Class
