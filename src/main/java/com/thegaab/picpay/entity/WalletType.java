package com.thegaab.picpay.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_wallet_type")
public class WalletType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @PrePersist
    private void assignId() {
        if ("user".equals(description)) {
            this.id = 1L;
        } else if ("merchant".equals(description)) {
            this.id = 2L;
        }
    }

    public WalletType() {
    }

    public WalletType(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Enum {

        USER( "user"),
        MERCHANT("merchant");

        private final String description;

        Enum(String description) {
            this.description = description;
        }

        public WalletType get() {
            return new WalletType(null, description);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WalletType that = (WalletType) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
