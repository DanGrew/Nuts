package uk.dangrew.nuts.graphics.progress.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.javafx.style.Conversions;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class UiProgressSeriesSummary extends GridPane {
   
   static final String TEXT_ONLY_ENTRY = "Text only entry.";
   static final String COLUMN_HEADER_TIMESTAMP = "Timestamp";
   static final String COLUMN_HEADER_ENTRY = "Entry";
   
   private static final Color HEADER_BACKGROUND = Color.BLACK;
   private static final Color TITLE_BACKGROUND = Color.CORNFLOWERBLUE;
   private static final Color DATE_BACKGROUND = Color.GRAY;
   private static final Color CONTENT_BACKGROUND = Color.WHITE;
   
   private static final Color LIGHT_TEXT_COLOUR = Color.WHITE;
   private static final Color DARK_TEXT_COLOUR = Color.BLACK;
   
   private final DateTimeFormats formats;
   private final Conversions conversions;
   private final JavaFxStyle styling;
   private final ProgressSeries series;
   
   private final ScrollPane contentScroller;
   private final GridPane content;
   
   private int rowCount;
   
   public UiProgressSeriesSummary( ProgressSeries series ) {
      this.series = series;
      this.conversions = new Conversions();
      this.formats = new DateTimeFormats();
      this.styling = new JavaFxStyle();
      
      this.styling.configureConstraintsForRowPercentages( this, 5, 95 );
      this.styling.configureConstraintsForEvenColumns( this, 1 );
      this.content = new GridPane();
      this.contentScroller = new ScrollPane( content );
      this.add( contentScroller, 0, 1 );
      GridPane.setColumnSpan( contentScroller, 2 );
      this.contentScroller.setFitToWidth( true );
      this.contentScroller.setFitToHeight( true );
      this.contentScroller.setPrefWidth( 800 );
      this.styling.configureConstraintsForColumnPercentages( content, 50, 50 );
      
      this.addTitle();
      this.addColumnHeaders();
      this.addContent();
   }//End Class
   
   private void addContent() {
      LocalDate currentDate = null;
      for ( LocalDateTime timestamp : series.entries() ) {
         if ( currentDate == null || !currentDate.equals( timestamp.toLocalDate() ) ) {
            currentDate = timestamp.toLocalDate();
            addDateRow( timestamp );
         }
         
         addRowForValue( timestamp );
         if ( series.headers().entryFor( timestamp ) != null || series.notes().entryFor( timestamp ) != null ) {
            addRowForText( timestamp );
         }
      }
   }//End Method
   
   private void addTitle() {
      Label title = basicBoldLabel( series.properties().nameProperty().get(), TITLE_BACKGROUND, LIGHT_TEXT_COLOUR );
      add( title, 0, 0 );
      GridPane.setColumnSpan( title, 2 );
   }//End Method
   
   private void addColumnHeaders() {
      Label timestampHeader = basicBoldLabel( COLUMN_HEADER_TIMESTAMP, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      Label entryHeader = basicBoldLabel( COLUMN_HEADER_ENTRY, HEADER_BACKGROUND, LIGHT_TEXT_COLOUR );
      addRow( timestampHeader, entryHeader );
   }//End Method
   
   private void addRowForValue( LocalDateTime timestamp ) {
      Label timestampLabel = basicLabel( formats.toTimeTimestampString( timestamp.toLocalTime() ), CONTENT_BACKGROUND, DARK_TEXT_COLOUR );
      Double value = series.values().entryFor( timestamp );
      String stringInformation = null;
      if ( value == null ) {
         stringInformation = TEXT_ONLY_ENTRY;
      } else {
         stringInformation = conversions.format( value );  
      }
      Label entryLabel = basicLabel( stringInformation, CONTENT_BACKGROUND, DARK_TEXT_COLOUR );
      addRow( timestampLabel, entryLabel );
   }//End Method
   
   private void addRowForText( LocalDateTime timestamp ) {
      addRow( new TextFlowBuilder()
         .withBackground( CONTENT_BACKGROUND )
         .withBorder( DATE_BACKGROUND, 0.5 )
         .withPadding( 5 )
         .bold( series.headers().entryFor( timestamp ) )
         .newLine()
         .normal( series.notes().entryFor( timestamp ) )
         .build() 
      );
   }//End Method
   
   private void addDateRow( LocalDateTime timestamp ) {
      addRow( basicBoldLabel( formats.toDateTimestampString( timestamp.toLocalDate() ), DATE_BACKGROUND, LIGHT_TEXT_COLOUR ) );
   }//End Method
   
   private Label basicLabel( String text, Color backgroundColour, Color textColour ) {
      return new LabelBuilder()
               .withText( text )
               .withTextColour( textColour )
               .withBackgroundColour( backgroundColour )
               .withMaxWidth()
               .withMaxHeight()
               .withPadding( 5 )
               .withBorder( DATE_BACKGROUND, 0.5 )
               .build();
   }//End Method
   
   private Label basicBoldLabel( String text, Color bakcgroundColour, Color textColour ) {
      return new LabelBuilder()
               .withText( text )
               .withTextColour( textColour )
               .withBackgroundColour( bakcgroundColour )
               .withMaxWidth()
               .withMaxHeight()
               .withPadding( 5 )
               .withBorder( DATE_BACKGROUND, 0.5 )
               .asBold()
               .build();
   }//End Method
   
   private void addRow( Label timetstamp, Label entry ) {
      content.add( timetstamp, 0, rowCount );
      content.add( entry, 1, rowCount );
      rowCount++;
   }//End Method
   
   private void addRow( Node date ) {
      content.add( date, 0, rowCount );
      GridPane.setColumnSpan( date, 2 );
      rowCount++;
   }//End Method
   
}//End Class
