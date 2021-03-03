package UN.Miage.covoit;

import android.view.ViewGroup;

public abstract class DrawerItem <T extends DrawerAdapter.ViewHolder> {
    protected boolean isChecked;
    public abstract T cereateViewHolder( ViewGroup parent);
    public abstract  void bindViewHolder(T holder);
    public DrawerItem<T>setChecked(boolean isChecked){
        this.isChecked= isChecked;
        return this;
    }
    public boolean isChecked(){
        return isChecked;
    }
    public boolean isSelectable(){
        return true;
    }
}
