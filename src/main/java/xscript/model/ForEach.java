package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class ForEach extends Block implements Command {
    private String itemName;
    private String indexName;
    private String collectionExpression;

    @XmlAttribute(name="item")
    public String getItemName() {
        return itemName;
    }

    @XmlAttribute(name="item")
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @XmlAttribute(name="index")
    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @XmlAttribute(name="collection")
    public String getCollectionExpression() {
        return collectionExpression;
    }

    public void setCollectionExpression(String collectionExpression) {
        this.collectionExpression = collectionExpression;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
