package uk.dangrew.nuts.graphics.research;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.research.ResearchArticle;
import uk.dangrew.nuts.research.ResearchArticleStore;

public class UiResearchController {

   private UiResearchPane pane;
   
   private final ObservableList< ResearchArticle > articles;
   private final ObjectProperty< ResearchArticle > selected;
   
   public UiResearchController( ResearchArticleStore store ) {
      this.selected = new SimpleObjectProperty<>();
      this.articles = FXCollections.observableArrayList();
      this.articles.addAll( store.objectList() );
   }//End Constructor
   
   public void associate( UiResearchPane pane ) {
      this.pane = pane;
   }//End Method

   public ObservableList< ResearchArticle > articles() {
      return articles;
   }//End Method

   public void show( ResearchArticle article ) {
      selected.set( article );
      pane.show( article );
   }//End Method

   public ObjectProperty< ResearchArticle > selected() {
      return selected;
   }//End Method

}//End Class
