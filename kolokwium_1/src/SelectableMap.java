public class SelectableMap extends VoivodeshipMap {
    private String selectedVoivodeship = null;
    
    public void select(String voivodeship) {
        this.selectedVoivodeship = voivodeship;
    }
    
    @Override
    protected String getVoivodeshipColor(String voivodeship) {
        if (voivodeship.equals(selectedVoivodeship)) {
            return "#ff0000"; // czerwony
        }
        return "#000000"; // czarny
    }
}
