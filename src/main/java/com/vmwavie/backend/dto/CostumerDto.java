package com.vmwavie.backend.dto;

import com.vmwavie.backend.model.Costumer;
import java.util.List;
import java.util.Optional;

public class CostumerDto {
    public static class CostumerResponseListAllDto {
        private List<Costumer> costumers;
        private String errorMessage;

        public CostumerResponseListAllDto(List<Costumer> costumers) {
            this.costumers = costumers;
            this.errorMessage = null;
        }

        public CostumerResponseListAllDto(String errorMessage) {
            this.costumers = null;
            this.errorMessage = errorMessage;
        }

        public List<Costumer> getCostumers() {
            return costumers;
        }

        public void setCostumers(List<Costumer> costumers) {
            this.costumers = costumers;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class CostumerResponseListOneDto {
        private Costumer costumer;
        private String errorMessage;

        public CostumerResponseListOneDto(Costumer costumer) {
            this.costumer = costumer;
            this.errorMessage = null;
        }

        public CostumerResponseListOneDto(String errorMessage) {
            this.costumer = null;
            this.errorMessage = errorMessage;
        }

        public Costumer getCostumer() {
            return costumer;
        }

        public void setCostumer(Costumer costumer) {
            this.costumer = costumer;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class CostumerResponseSaveDto {
        private Costumer costumer;
        private String errorMessage;

        public Costumer getCostumer() {
            return costumer;
        }

        public void setCostumer(Costumer costumer) {
            this.costumer = costumer;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public static class CostumerResponseUpdateDto {
        private Costumer costumer;
        private String errorMessage;

        public Costumer getCostumer() {
            return costumer;
        }

        public void setCostumer(Costumer costumer) {
            this.costumer = costumer;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}