package roundwar;

import screenControl.AbstractScreen;
import Buttons.ImageCharacter;
import Entities.LivingEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class Scroll extends ScrollPane {
    
    public static final String SEPARATOR = "/*~*/";
   
    private MyRawList rawList;
    private boolean isEnabled;
    private String emptyLabel;   
           
    /**
     * Constructor
     * @param items To create items use ScrollItem.formItems(names, urls)
     * @param style
     * @param listStyle
     * @param placeholder It must not be empty!
     * @param useLocalStorage If true, downloaded images are stored relative to private storage of the app.
     *                      Otherwise, they are stored relative to SD card root (Neends WRITE_EXTERNAL_STORAGE permission).        
     */
    public Scroll() {            
        super(null);    
        isEnabled = true;
        rawList = new MyRawList(null, this);
        setWidget(rawList);
        setWidth(rawList.getPrefWidth());
        setHeight(rawList.getPrefHeight());
        setScrollingDisabled(true, false);     
        emptyLabel = "There are no scores yet";
    }
                           
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;            
    }
           
    public void setItems(ScrollItem[] items){
        rawList.setItems(items);
        super.setForceScroll(false, true);
    }
   
    public String getEmptyLabel() {
        return emptyLabel;
    }

    public void setEmptyLabel(String emptyLabel) {
        this.emptyLabel = emptyLabel;
    }
                                                   
    /******************** RAW LIST ************************/
    public class MyRawList extends Widget {
           
        private Scroll Scroll;
        private ScrollItem[] items;
        private float prefWidth, prefHeight;
        private float itemSize = (int) (Gdx.graphics.getWidth()*0.1f);
        private float textOffsetX, textOffsetY;
        private float coefH;
       
        public MyRawList (ScrollItem[] items, Scroll Scroll) {
            coefH = Gdx.graphics.getHeight()/320f;
            
            setItems(items);
            setWidth(getPrefWidth());
            setHeight(getPrefHeight());            
            this.Scroll=Scroll;           
        }  
                       
        @Override
        public void draw (SpriteBatch batch, float parentAlpha) {
            BitmapFont font = AbstractScreen.getFont();

            float x = getX();
            float y = getY();
            
            float itemY = getHeight();
           
            if(items==null){
                    float centerX = Scroll.getWidth()/2;
                    float centerY = Scroll.getHeight()/2;
                    font.draw(batch, emptyLabel, centerX - font.getBounds(emptyLabel).width/2,
                                    centerY+font.getBounds(emptyLabel).height/2);             
                    return;
            }
            
            for (int i = 0; i < items.length; i++) {
                boolean isDrawn = true;
                
                if(getHeight()-itemY < Scroll.getScrollY() - itemSize) {
                    isDrawn=false;
                }                              
                if(getHeight()-itemY > Scroll.getScrollY() + Scroll.getHeight()) {
                    isDrawn = false;
                }
               
                if(isDrawn) {
                	// Imagen del personaje
                	items[i].getImage().setBounds(x, y + itemY - textOffsetY*coefH - items[i].getImage().getHeight(), itemSize, itemSize);
                	items[i].getImage().draw(batch, parentAlpha);
                	
                	// Experiencia
                	font.draw(batch, items[i].getExperience(),
                             x + textOffsetX + getWidth()*0.25f,
                             y + itemY - textOffsetY*coefH - font.getBounds(items[i].getExperience()).height/2);
                	
                	// Nivel
                	font.draw(batch, "Lvl: " + items[i].getLvl(),
                            x + textOffsetX + getWidth()*0.7f,
                            y + itemY - textOffsetY*coefH - font.getBounds("Lvl: " + items[i].getLvl()).height/2);
                }
                                                       
                itemY -= itemSize;
            }
        }
        
        public void setItems (ScrollItem[] objects) {
            if(objects == null){
                items = null;
                return;
            }                      
            if (!(objects instanceof ScrollItem[])) {                     
                throw new IllegalArgumentException("Items must be instance of ScrollItem");
            }
                   
            items = objects;               
        }
                                                                           
        public ScrollItem[] getItems () {
            return items;
        }

        @Override
		public float getPrefWidth () {
            return prefWidth;
        }

        @Override
		public float getPrefHeight () {
            return prefHeight;
        }               
    }      
   
    /************************ LIST ITEM ************************/
    public static class ScrollItem implements Comparable<ScrollItem>{  
	    private LivingEntity.Type type;
		private ImageCharacter image;
		private int lvlint, experienceint;
        private String lvl, experience;      
        private static Texture tex;
        private static TextureRegion[][] fronts;
        private static NinePatch tbg;
        
        public ScrollItem(LivingEntity.Type type, int lvl, int experience){
            this.type = type;
            this.lvlint = lvl;
            this.lvl = String.valueOf(lvl);
            this.experienceint = experience;
            this.experience = String.valueOf(experience);  
            
            switch(this.type) {
			case GULLA:
				image = new ImageCharacter(tbg, fronts[1][1]);
				break;
			case PIRKO:
				image = new ImageCharacter(tbg, fronts[1][0]);
				break;
			default:
				image = new ImageCharacter(tbg, fronts[0][2]);
				break;
            }
            image.setSize(Gdx.graphics.getHeight()*0.17f, Gdx.graphics.getHeight()*0.17f);
        }
        
        public static void initialize() {
//        	texBg = new Texture(Gdx.files.internal("images/prueba.png"));
        	tbg = AbstractScreen.getSkin().getPatch("bg-info");// new TextureRegion(texBg,0,0,85,85);
        	tex = new Texture(Gdx.files.internal("images/front.png"));
    		fronts = TextureRegion.split(tex, 64, 64);
        }
                                       
        public String getExperience() {
			return experience;
		}

		public void setExperience(String experience) {
			this.experience = experience;
		}

		public String getLvl() {
			return lvl;
		}

		public void setLvl(String lvl) {
			this.lvl = lvl;
		}
		
		public int getExperienceInt() {
			return experienceint;
		}

		public int getLvlInt() {
			return lvlint;
		}

		public ImageCharacter getImage() {
			return image;
		}

		public void setImage(ImageCharacter image) {
			this.image = image;
		}

		/**
         * Forms array of items (size of arrays must be equal)
         * @param texts If you want to have several columns, you should split them by SEPARATOR
         * @param urls URLs of the images which has to be downloaded
         * @return
         */
        public static ScrollItem[] formItems(LivingEntity.Type[] type, int[] lvl, int[] experience) {
            if(type.length!=lvl.length && type.length!=experience.length) return null;                             
            ScrollItem[] listItems = new ScrollItem[type.length];                       
            for (int i=0; i<type.length; i++)         
              listItems[i] = new ScrollItem(type[i], lvl[i], experience[i]);                             
            return listItems;
        }

		@Override
		public int compareTo(ScrollItem o) {
			return (o.getExperienceInt() >= experienceint)? 1 : -1;
		}
    }      
   
    public static class ImagePow2 {
           
            static int pow2[] = {2,4,8,16,32,64,128,256,512,1024,2048,4096,8192};          
            public static boolean isPow2(Pixmap pixmap){
                    boolean isWidthPow2 = false, isHeightPow2 = false;
                    for(int i=0; i<pow2.length; i++){
                            if(pow2[i]==pixmap.getWidth()) isWidthPow2=true;
                            if(pow2[i]==pixmap.getHeight()) isHeightPow2=true;
                    }
                    return isWidthPow2&&isHeightPow2;
            }
            private static int getPow2(int value){
                    int diff = value;
                    int tmp = value;
                    int minVal = value;
                    for(int i=0; i<pow2.length; i++){
                            diff = Math.abs(value - pow2[i]);
                            if(tmp>diff){
                                    tmp=diff;
                                    minVal = pow2[i];
                            }
                    }
                    return minVal;
            }
           
            public static Pixmap getPow2Pixmap(Pixmap pixmap){
                int width = getPow2(pixmap.getWidth());
		        int height = getPow2(pixmap.getHeight());
		        Pixmap updatedPixmap = new Pixmap(width, height, pixmap.getFormat());
		        updatedPixmap.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(), 0, 0, width, height);
		        return updatedPixmap;
            }                              
    }
}

/**How to use**/
/*
Scroll myLazyListTop100 = new Scroll(null, scrollPaneStyle, listStyle,
                            new Texture(Gdx.files.internal("skins/unknown_flag.png")), false);                             
            myLazyListTop100.setEnabled(false);
            String splitter = Scroll.SEPARATOR;
      //MyDrawable implements Drawable
myLazyListTop100.setHeaders("#"+splitter+"Nickname"+splitter+"Location"+splitter+"Score"+splitter+"Flag",
                            new MyDrawable(new NinePatch(new Texture(Gdx.files.internal("images/background_list.png")))));
            myLazyListTop100.setHeadersColor(new Color(0, 1, 1, 1));
           
myLazyListTop100.setBounds(5, 5, width, height );
    myLazyListTop100.setEnabled(false);
    myLazyListTop100.setSelectedIndex(-1);
    myLazyListTop100.setItems(null);
    myLazyListTop100.setEmptyLabel("Loading...");
    stage.addActor(myLazyListTop100);
*/