package safeBank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
        public enum Type{
            WITHDRAWAL,
            DEPOSIT
        }

        private final Type type;
        private final BigDecimal amount;
        private final LocalDateTime time;

        public Transaction(Type type, BigDecimal amount){
            this.type = type;
            this.amount = amount;
            this.time = LocalDateTime.now();

        }

        public Type getType() {
            return type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public  LocalDateTime getTime() {
            return time;
        }

        @Override
        public String toString() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return type + "| ₦" + amount.toPlainString() + " | " + time.format(dtf);
        }
    }


