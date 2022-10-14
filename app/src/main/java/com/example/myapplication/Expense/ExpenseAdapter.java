package com.example.myapplication.Expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Trip.Trip;
import com.example.myapplication.Trip.TripAdapter;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenses;

    public void setData(List<Expense> list){
        this.expenses = list;
        notifyDataSetChanged();
    }


    public interface IClickItemExpense{
        void updateExpense(Expense expense);

    }

    private ExpenseAdapter.IClickItemExpense iClickItemExpense;

    public ExpenseAdapter(ExpenseAdapter.IClickItemExpense iClickItemExpense) {
        this.iClickItemExpense = iClickItemExpense;
    }


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);

        if(expense == null){
            return;
        }

        holder.tvExpenseId.setText(String.valueOf(position + 1));
        holder.tvExpenseType.setText(expense.getType());
        holder.tvExpenseAmount.setText(expense.getAmount().toString());
        holder.tvExpenseDate.setText(expense.getDate());

    }


    @Override
    public int getItemCount() {
        if(expenses != null){
            return expenses.size();
        }
        return 0;
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{

        private TextView tvExpenseId;
        private TextView tvExpenseType;
        private TextView tvExpenseDate;
        private TextView tvExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Expense expense = expenses.get(getAdapterPosition());
                    iClickItemExpense.updateExpense(expenses.get(getAdapterPosition()));
                }
            });

            tvExpenseId = itemView.findViewById(R.id.tv_expense_id);
            tvExpenseType = itemView.findViewById(R.id.tv_expense_type);
            tvExpenseDate = itemView.findViewById(R.id.tv_expense_date);
            tvExpenseAmount = itemView.findViewById(R.id.tv_expense_amount);

        }
    }
}
