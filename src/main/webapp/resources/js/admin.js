/**
 * @author thiago
 * @version v1.0.0 31/01/2017
 * @since v1.0.0
 */
$(document).ready(function() {
    var dados = '#dados';
    $(document).on('mouseover', dados, function() {
        $(dados).prop('src', "/sep/javax.faces.resource/dado-vermelho.png.xhtml?ln=imagens");
        $(dados).css('width', '12.5%');
        $(dados).css('height', '12.5%');
    });
    $(document).on('mouseout', dados, function() {
        $(dados).prop('src', "/sep/javax.faces.resource/dado-azul.png.xhtml?ln=imagens");
        $(dados).css('width', '10%');
        $(dados).css('height', '10%');
    });
}); 
