
package model.enums;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public enum FormaPagamento {
    DINHEIRO,
    PRAZO,
    CREDITO_VISTA,
    CREDITO_PRAZO,
    CREDITO_PARCELADO,
    DEBITO_VISTA,
    DEBITO_PRAZO,
    DEBITO_PARCELADO,
    PIX;
    
    public String getNome() {
        return switch (this) {
            case DINHEIRO -> "Dinheiro";
            case PRAZO -> "À prazo";
            case CREDITO_VISTA -> "Crédito à vista";
            case CREDITO_PRAZO -> "Crédito à prazo";
            case CREDITO_PARCELADO -> "Crédito parcelado";
            case DEBITO_VISTA -> "Débito à vista";
            case DEBITO_PRAZO -> "Débito à prazo";
            case DEBITO_PARCELADO -> "Débito parcelado";
            case PIX -> "Pix";
            default -> "";
        };
    }
    
    public static FormaPagamento getFormaPagamento(int id){
        return switch (id) {
            case 1 -> DINHEIRO;
            case 2 -> PRAZO;
            case 3 -> CREDITO_VISTA;
            case 4 -> CREDITO_PRAZO;
            case 5 -> CREDITO_PARCELADO;
            case 6 -> DEBITO_VISTA;
            case 7 -> DEBITO_PRAZO;
            case 8 -> DEBITO_PARCELADO;
            case 9 -> PIX;
            default -> DINHEIRO;
        };
    }
    
    public int getIdFormaPagamento() {
        return switch (this) {
            case DINHEIRO -> 1;
            case PRAZO -> 2;
            case CREDITO_VISTA -> 3;
            case CREDITO_PRAZO -> 4;
            case CREDITO_PARCELADO -> 5;
            case DEBITO_VISTA -> 6;
            case DEBITO_PRAZO -> 7;
            case DEBITO_PARCELADO -> 8;
            case PIX -> 9;
            default -> 0;
        };
    }
}
