export interface IAllergen {
    name: string;
    description: string;
    imageUrl: string;
}

export class Allergen {
    private name: string;
    private description: string;
    private imageUrl: string;

    constructor(allergen?: IAllergen) {
        if (allergen) {
            this.name = allergen.name;
            this.description = allergen.description;
            this.imageUrl = allergen.imageUrl;
        }
    }

    public getName(): string {
        return this.name;
    }

    public setName(name: string): void {
        this.name = name;
    }

    public getDescription(): string {
        return this.description;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public getImageUrl(): string {
        return this.imageUrl;
    }

    public setImageUrl(imageUrl: string): void {
        this.imageUrl = imageUrl;
    }
}
