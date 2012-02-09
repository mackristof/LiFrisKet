package liFrisKet;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/** Porlet de saison 
 * @author mackristof
 * 
 */
public class LiFrisKetApplication extends Application {

	public void init() {
		Window window = new Window();

		setMainWindow(window);
		final HorizontalLayout hp = new HorizontalLayout();
		final Label label1 = new Label("Avec une tempature de ");
		label1.setWidth("150px");
		hp.addComponent(label1);
		final TextField temp = new TextField();
		temp.setWidth("20px");
		hp.addComponent(temp);
		final Label label2 = new Label("° et un vent à ");
		label2.setWidth("100px");
		hp.addComponent(label2);
		final TextField wind = new TextField();
		wind.setWidth("20px");
		hp.addComponent(wind);
		final Label label3 = new Label(" km/h  produit une températeure ressentie ");
		label3.setWidth("200px");
        hp.addComponent(label3);
		final Label resultLabel = new Label();
        
		final Button submit =  new Button("de", new ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                Double result = getTemperatureRessentie(Double.valueOf((String)temp.getValue()), Double.valueOf((String)wind.getValue()));
                resultLabel.setValue(result);
                
                hp.addComponent(resultLabel);
            }
        });
		hp.addComponent(submit);
		window.addComponent(hp);
	}
	
	/**
     * Estime la temperature ressentie en fonction du vent et de la température.
     *
     * <blockquote><font size=-1> <strong>NOTE: Formule definie pour : -
     * temperartureAmbiante : de -50°C a +10°C. - vitesseVent : de 0 à 177
     * Km/h.</strong> </font></blockquote>
     *
     * @param temperartureAmbiante formule definie en °C.
     * @param vitesseVent en Km/h.
     * @return temperature ressentie en °C.
     * @author remi.marechal@geomatys.com
     */
    public static double getTemperatureRessentie(final double temperartureAmbiante, final double vitesseVent) {
        if (vitesseVent < 0 || vitesseVent > 177) {
            throw new IllegalArgumentException("formule non definie pour ce cas");
        }
        if (temperartureAmbiante > 10 || temperartureAmbiante < -50) {
            return temperartureAmbiante;
        } else {
            if (vitesseVent <= 4.8) {
                return temperartureAmbiante + 0.2 * vitesseVent * (0.1345 * temperartureAmbiante - 1.59);
            } else {
                return 13.12 + 0.6215 * temperartureAmbiante + (0.3965 * temperartureAmbiante - 11.37) * Math.pow(vitesseVent, 0.16);
            }
        }
    }

}