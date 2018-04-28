/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.weight;

import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.progress.weight.WeighIn;

/**
 * {@link WeightRecordingGraphSeriesBuilder} provides the builder pattern with properties for
 * configuring a {@link javafx.scene.chart.XYChart.Series} in the {@link WeightRecordingGraph}.
 */
public class WeightRecordingGraphSeriesBuilder {

   private String seriesName;
   private Function< WeighIn, ObjectProperty< Double > > propertyRetriever;
   
   /**
    * Configures the series name.
    * @param value the name.
    * @return this.
    */
   public WeightRecordingGraphSeriesBuilder withName( String value ) {
      this.seriesName = value;
      return this;
   }//End Method
   
   /**
    * Access to the {@link javafx.scene.chart.XYChart.Series} name.
    * @return the name.
    */
   public String seriesName() {
      return seriesName;
   }//End Method
   
   /**
    * Configures the {@link Function} for obtaining the property being represented by the {@link javafx.scene.chart.XYChart.Series}.
    * @param value the function.
    * @return this.
    */
   public WeightRecordingGraphSeriesBuilder forProperty( Function< WeighIn, ObjectProperty< Double > > retriever ) {
      this.propertyRetriever = retriever;
      return this;
   }//End Method

   /**
    * Access to the {@link Function} for obtaining the property being represented.
    * @return the {@link Function}.
    */
   public Function< WeighIn, ObjectProperty< Double > > propertyRetriever() {
      return propertyRetriever;
   }//End Method

}//End Class
