package com.forseti.drilltracker.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.forseti.drilltracker.data.Category;
import com.forseti.drilltracker.data.Drill;
import com.forseti.drilltracker.DrillTrackerMain;
import com.forseti.drilltracker.R;
import com.forseti.drilltracker.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class ExpandableDrillListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> categories;

    public ExpandableDrillListAdapter(DrillTrackerMain drillTrackerMain, List<Category> categoryList) {
        this.context = drillTrackerMain;
        this.categories = categoryList;
    }

    @Override
    public int getGroupCount() {
        return this.categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Category category = (Category) getGroup(groupPosition);
        return category.getDrills().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Category category = (Category) getGroup(groupPosition);
        return category.getDrills().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Category category = (Category) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_row, null);
        }
        TextView danceName = (TextView) convertView.findViewById(R.id.category_name);
        danceName.setText(category.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Drill drill = (Drill) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.drill_row, null);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.drill_name);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.drill_description);

        nameTextView.setText(drill.getName());
        descriptionTextView.setText(drill.getDescription());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<String> getCategories() {
        List<String> categoryNames = new ArrayList<>(getGroupCount());

        for(Category category : categories) {
            categoryNames.add(category.getName());
        }

        return categoryNames;
    }

    public void addCategoryAndDrills(Context context, Category newCategory) {
        Category current = getCategory(newCategory.getName());
        current.addDrills(newCategory.getDrills());
        notifyDataSetChanged(context);
    }

    public void addCategory(Context context, Category newCategory) {
        categories.add(newCategory);
        notifyDataSetChanged(context);
    }

    private Category getCategory(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        Category category = new Category(name);
        categories.add(category);
        return category;
    }

    public void addDrill(Context context, int categoryPosition, Drill newDrill) {
        Category category = (Category) getGroup(categoryPosition);
        category.getDrills().add(newDrill);
        notifyDataSetChanged(context);
    }

    public void removeDrill(Context context, int categoryPosition, int drillPosition) {
        Log.i("Data Removal", "Category: " + categoryPosition + " | Drill: " + drillPosition);
        Category category = (Category) getGroup(categoryPosition);
        category.getDrills().remove(drillPosition);
        notifyDataSetChanged(context);
    }

    public void notifyDataSetChanged(Context context) {
        super.notifyDataSetChanged();
        DataUtils.saveData(context, this);
    }

    public void removeCategory(Context context, int groupId) {
        categories.remove(groupId);
        notifyDataSetChanged(context);
    }

    public void clearData(Context context) {
        categories.clear();
        DataUtils.saveData(context, this);
    }

    public void editName(Category category, String newName) {
        category.setName(newName);
        notifyDataSetChanged(context);
    }

    public void editDrill(Drill drill, String name, String summary, String instructions, String url) {
        drill.setName(name);
        drill.setDescription(summary);
        drill.setInstructions(instructions);
        drill.setVideoURL(url);
        notifyDataSetChanged(context);
    }

    public List<Category> getCategoryList() {
        return categories;
    }
}
