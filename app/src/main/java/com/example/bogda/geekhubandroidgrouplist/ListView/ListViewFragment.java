package com.example.bogda.geekhubandroidgrouplist.ListView;


import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.bogda.geekhubandroidgrouplist.R;
import com.example.bogda.geekhubandroidgrouplist.People;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        final ArrayList<People> peoples = new ArrayList<People>();

        peoples.add(new People("Евгений Жданов","https://plus.google.com/113264746064942658029","https://github.com/zhdanov-ek"));
        peoples.add(new People("Edgar Khimich","https://plus.google.com/102197104589432395674","https://github.com/lyfm"));
        peoples.add(new People("Alexander Storchak","https://plus.google.com/106553086375805780685","https://github.com/new15"));
        peoples.add(new People("Yevhenii Sytnyk","https://plus.google.com/101427598085441575303","https://github.com/YevheniiSytnyk"));
        peoples.add(new People("Alyona Prelestnaya","https://plus.google.com/107382407687723634701","https://github.com/HelenCool"));
        peoples.add(new People("Богдан Рибак","https://plus.google.com/103145064185261665176","https://github.com/BogdanRybak1996"));
        peoples.add(new People("Ірина Смалько","https://plus.google.com/113994208318508685327","https://github.com/IraSmalko"));
        peoples.add(new People("Владислав Винник","https://plus.google.com/117765348335292685488","https://github.com/vlads0n"));
        peoples.add(new People("Ігор Пахаренко","https://plus.google.com/108231952557339738781","https://github.com/IhorPakharenko"));
        peoples.add(new People("Андрей Рябко","https://plus.google.com/110288437168771810002","https://github.com/RyabkoAndrew"));
        peoples.add(new People("Ivan Leshchenko","https://plus.google.com/111088051831122657934","https://github.com/ivleshch"));
        peoples.add(new People("Микола Піхманець","https://plus.google.com/110087894894730430086","https://github.com/NikPikhmanets"));
        peoples.add(new People("Ruslan Migal","https://plus.google.com/106331812587299981536","https://github.com/rmigal"));
        peoples.add(new People("Руслан Воловик","https://plus.google.com/109719711261293841416","https://github.com/RuslanVolovyk"));
        peoples.add(new People("Valerii Gubskyi","https://plus.google.com/107910188078571144657","https://github.com/gvv-ua"));
        peoples.add(new People("Иван Сергеенко","https://plus.google.com/111389859649705526831","https://github.com/dogfight81"));
        peoples.add(new People("Вова Лымарь","https://plus.google.com/109227554979939957830","https://github.com/VovanNec"));
        peoples.add(new People("Даша Кириченко","https://plus.google.com/103130382244571139113","https://github.com/dashakdsr"));
        peoples.add(new People("Michael Tyoply","https://plus.google.com/110313151428733681846","https://github.com/RedGeekPanda"));
        peoples.add(new People("Павел Сакуров","https://plus.google.com/108482088578879737406","https://github.com/sakurov"));

        Collections.sort(peoples);

        final ListView listView = (ListView) rootView.findViewById(R.id.data_list_view);
        final PeopleAdapter adapter = new PeopleAdapter(getActivity(), peoples);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        final SwipeActionAdapter swipeAdapter = new SwipeActionAdapter(adapter);
        swipeAdapter.setListView(listView);
        listView.setAdapter(swipeAdapter);
        swipeAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if(direction.isLeft()) return true;
                if(direction.isRight()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                return true;
            }

            @Override
            public void onSwipe(int[] position, SwipeDirection[] direction) {
                final int curPos = position[0];
                final People people = peoples.get(curPos);
                peoples.remove(curPos);
                swipeAdapter.notifyDataSetChanged();

                Snackbar.make(rootView,people.getName() + " deleted",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                peoples.add(curPos,people);
                                swipeAdapter.notifyDataSetChanged();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

        return rootView;
    }

}
