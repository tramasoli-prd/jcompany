#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.model.domain;


/**
 * Valores discretos para Estado Civil, com descricoes I18n
 */
public enum Sexo {
	
	M("Masculino"),
	F("Feminino");

    /**
     * @return Retorna o codigo.
     */
	private String label;
    
    private Sexo(String label) {
    	this.label = label;
    }
     
    public String getLabel() {
        return label;
    }
}
